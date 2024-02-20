package com.kuit.conet.service;

import com.kuit.conet.domain.member.Member;
import com.kuit.conet.dto.web.response.notice.NoticeResponseDTO;
import com.kuit.conet.domain.notice.Notice;
import com.kuit.conet.repository.MemberRepository;
import com.kuit.conet.repository.NoticeRepository;
import com.kuit.conet.service.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kuit.conet.service.validator.MemberValidator.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final Long NOTICE_ID = 1L;

    public NoticeResponseDTO getNotice(Long memberId) {
        Member member = memberRepository.findById(memberId);
        validateMemberExisting(member);

        Notice notice = noticeRepository.findById(NOTICE_ID);
        return new NoticeResponseDTO(notice);
    }
}
