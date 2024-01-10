package com.kuit.conet.jpa.service;

import com.kuit.conet.dto.web.request.member.NameRequestDTO;
import com.kuit.conet.dto.web.response.StorageImgResponseDTO;
import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.storage.StorageDomain;
import com.kuit.conet.jpa.repository.MemberRepository;
import com.kuit.conet.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.kuit.conet.jpa.service.validator.MemberValidator.validateMemberExisting;
import static com.kuit.conet.service.StorageService.getFileName;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final StorageService storageService;

    public StorageImgResponseDTO updateImg(Long userId, MultipartFile file) {
        Member member = memberRepository.findById(userId);
        validateMemberExisting(member);

        deletePreviousImage(userId);

        // 저장할 파일명 만들기
        // 새로운 이미지 S3에 업로드
        String imgUrl = storageService.uploadToS3(file, getFileName(file, StorageDomain.USER));

        // 변경감지로 update
        member.updateImgUrl(imgUrl);
        return memberRepository.getImgUrlResponse(userId);
    }

    //TODO storageservice로 뻬
    private void deletePreviousImage(Long userId) {
        String imgUrl = memberRepository.getImgUrlResponse(userId).getImgUrl();
        String deleteFileName = storageService.getFileNameFromUrl(imgUrl);

        // 유저의 프로필 이미지가 기본 프로필 이미지인지 확인 -> 기본 이미지가 아니면 기존 이미지를 S3에서 이미지 삭제
        if (!memberRepository.isDefaultImage(userId)) {
            // S3 버킷에 존재하지 않는 객체인 경우 삭제를 생략
            storageService.deleteImage(deleteFileName);
        }
    }

    public void updateName(Long userId, NameRequestDTO nameRequest) {
        System.out.println("service " + userId);
        Member member = memberRepository.findById(userId);
        validateMemberExisting(member);

        member.updateName(nameRequest.getName());
    }

}
