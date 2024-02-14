package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.common.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.INVALID_REFRESH_TOKEN;
import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.IP_MISMATCH;

@Slf4j
public class AuthValidator {
    public static void validateRefreshTokenExisting(String existingIp) {
        if (existingIp == null) {
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN);
        }
    }

    public static void compareClientIpFromRedis(String existingIp, String clientIp) {
        if (!existingIp.equals(clientIp)) {
            throw new InvalidTokenException(IP_MISMATCH);
        }
    }
}
