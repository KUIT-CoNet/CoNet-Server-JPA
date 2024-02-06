package com.kuit.conet.jpa.service;

import com.kuit.conet.dto.web.request.member.NameRequestDTO;
import com.kuit.conet.dto.web.response.member.StorageImgResponseDTO;
import com.kuit.conet.dto.web.response.member.MemberResponseDTO;
import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.storage.StorageDomain;
import com.kuit.conet.jpa.repository.MemberRepository;
import com.kuit.conet.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.kuit.conet.jpa.service.validator.MemberValidator.validateActiveMember;
import static com.kuit.conet.jpa.service.validator.MemberValidator.validateMemberExisting;
import static com.kuit.conet.service.StorageService.getFileName;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final StorageService storageService;
    @Value("${spring.user.default-image}")
    private String defaultImg;

    public StorageImgResponseDTO updateImg(Long userId, MultipartFile file) {
        Member member = memberRepository.findById(userId);
        validateMemberExisting(member);
        validateActiveMember(member);

        storageService.deletePreviousImage(userId);

        // 저장할 파일명 만들기
        // 새로운 이미지 S3에 업로드
        String imgUrl = storageService.uploadToS3(file, getFileName(file, StorageDomain.USER));

        // 변경감지로 update
        member.updateImgUrl(imgUrl);
        return memberRepository.getImgUrlResponse(userId);
    }

    public void updateName(Long userId, NameRequestDTO nameRequest) {
        Member member = memberRepository.findById(userId);
        validateMemberExisting(member);
        validateActiveMember(member);

        member.updateName(nameRequest.getName());
    }

    public MemberResponseDTO getUser(Long userId) {
        Member member = memberRepository.findById(userId);
        validateMemberExisting(member);
        validateActiveMember(member);

        String fileName = storageService.getFileNameFromUrl(member.getImgUrl());
        // S3에 없는 객체에 대한 유효성 검사
        if (!storageService.isExistImage(fileName)) {
            log.warn("S3 버킷에 존재하지 않는 이미지입니다. 기본 이미지로 변경하겠습니다.");
            // 변경감지로 update
            member.updateImgUrl(defaultImg);
        }

        return new MemberResponseDTO(member);
    }

}
