package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.common.exception.UserException;
import com.kuit.conet.jpa.domain.member.Member;
import lombok.extern.slf4j.Slf4j;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.NOT_FOUND_USER;

@Slf4j
public class MemberValidator {
    public static void validateMemberExisting(Member member){
        if (member == null) {
            throw new UserException(NOT_FOUND_USER);
        }
    }
}
