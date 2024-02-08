package com.kuit.conet.jpa.service;

import com.kuit.conet.dto.web.response.notice.NoticeResponseDTO;
import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.notice.Notice;
import com.kuit.conet.jpa.repository.MemberRepository;
import com.kuit.conet.jpa.repository.NoticeRepository;
import com.kuit.conet.jpa.service.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final Long NOTICE_ID = 1L;

    public NoticeResponseDTO getNotice(Long userId) {
        Member member = memberRepository.findById(userId);
        MemberValidator.validateMemberExisting(member);

        Notice notice = noticeRepository.findById(NOTICE_ID);
        return new NoticeResponseDTO(notice);
    }
}
