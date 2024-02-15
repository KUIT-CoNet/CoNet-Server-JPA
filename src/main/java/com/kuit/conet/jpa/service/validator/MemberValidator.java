package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.common.exception.UserException;
import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.member.MemberStatus;
import lombok.extern.slf4j.Slf4j;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
public class MemberValidator {
    public static void validateMemberExisting(Member member) {
        if (member == null) {
            log.error(NOT_FOUND_USER.getMessage());
            throw new UserException(NOT_FOUND_USER);
        }
    }

    public static void validateActiveMember(Member member) {
        if (!(member.getStatus() == MemberStatus.ACTIVE)) {
            log.error(INACTIVE_USER.getMessage());
            throw new UserException(INACTIVE_USER);
        }
    }

}
