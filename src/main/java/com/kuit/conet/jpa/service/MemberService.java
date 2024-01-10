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

        storageService.deletePreviousImage(userId);

        // 저장할 파일명 만들기
        // 새로운 이미지 S3에 업로드
        String imgUrl = storageService.uploadToS3(file, getFileName(file, StorageDomain.USER));

        // 변경감지로 update
        member.updateImgUrl(imgUrl);
        return memberRepository.getImgUrlResponse(userId);
    }

    public void updateName(Long userId, NameRequestDTO nameRequest) {
        System.out.println("service " + userId);
        Member member = memberRepository.findById(userId);
        validateMemberExisting(member);

        member.updateName(nameRequest.getName());
    }

}
