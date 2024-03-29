package com.kuit.conet.common.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus {
    /**
     * 1000: 요청 성공 (OK)
     */
    SUCCESS(1000, HttpStatus.OK.value(), "요청에 성공하였습니다."),

    /**
     * 2000: Request 오류 (BAD_REQUEST)
     */
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 요청입니다."),
    URL_NOT_FOUND(2001, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 URL 입니다."),
    METHOD_NOT_ALLOWED(2002, HttpStatus.METHOD_NOT_ALLOWED.value(), "해당 URL에서는 지원하지 않는 HTTP Method 입니다."),
    INAPPROPRIATE_DATA(2003, HttpStatus.BAD_REQUEST.value(), "입력한 정보가 올바르지 않습니다."),

    /**
     * 3000: Server, Database 오류 (INTERNAL_SERVER_ERROR)
     */
    SERVER_ERROR(3000, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에서 오류가 발생하였습니다."),
    DATABASE_ERROR(3001, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스에서 오류가 발생하였습니다."),
    BAD_SQL_GRAMMAR(3002, HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL에 오류가 있습니다."),
    TIME_OUT_OF_BOUND(3003, HttpStatus.INTERNAL_SERVER_ERROR.value(), "범위를 벗어난 날짜 정보가 존재합니다."),

    /**
     * 4000: Authorization 오류
     */
    JWT_ERROR(4000, HttpStatus.UNAUTHORIZED.value(), "JWT에서 오류가 발생하였습니다."),
    TOKEN_NOT_FOUND(4001, HttpStatus.BAD_REQUEST.value(), "토큰이 HTTP Header에 없습니다."),
    UNSUPPORTED_TOKEN_TYPE(4002, HttpStatus.UNAUTHORIZED.value(), "지원되지 않는 토큰 형식입니다."),
    UNSUPPORTED_ID_TOKEN_TYPE(4003, HttpStatus.UNAUTHORIZED.value(), "OAuth Identity Token의 형식이 올바르지 않습니다."),
    INVALID_TOKEN(4007, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰입니다."),
    MALFORMED_TOKEN(4008, HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 토큰입니다."),
    EXPIRED_TOKEN(4009, HttpStatus.UNAUTHORIZED.value(), "로그인 인증 유효 기간이 만료되었습니다."),
    TOKEN_MISMATCH(4010, HttpStatus.UNAUTHORIZED.value(), "로그인 정보가 토큰 정보와 일치하지 않습니다."),
    INVALID_CLAIMS(4011, HttpStatus.UNAUTHORIZED.value(), "OAuth Claims 값이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN(4012, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 Refresh Token입니다."),
    IP_MISMATCH(4013, HttpStatus.UNAUTHORIZED.value(), "다른 IP에서 접속했습니다. 다시 로그인해주세요."),
    OPTION_TERM_ALREADY_SET(4014, HttpStatus.UNAUTHORIZED.value(), "입력한 선택 약관의 선택 여부와 기존 선택 약관의 선택 여부가 동일합니다."),

    /**
     * 5000: 회원 정보 오류
     */
    INVALID_PLATFORM(5001, HttpStatus.BAD_REQUEST.value(), "플랫폼 정보가 올바르지 않습니다."),
    NOT_FOUND_MEMBER(5002, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 사용자입니다."),
    INACTIVE_MEMBER(5003, HttpStatus.BAD_REQUEST.value(), "활성화 상태가 아닌 사용자입니다."),

    /**
     * 5500: 모임(Team) 정보 오류
     */

    NOT_FOUND_INVITE_CODE(5501, HttpStatus.NOT_FOUND.value(), "존재하지 않는 초대 코드입니다."),
    EXPIRED_INVITE_CODE(5502, HttpStatus.BAD_REQUEST.value(), "초대 코드 유효 기간이 만료되었습니다."),
    EXIST_MEMBER_IN_TEAM(5503, HttpStatus.BAD_REQUEST.value(), "모임에 이미 참여 중입니다."),
    NOT_TEAM_MEMBER(5504, HttpStatus.BAD_REQUEST.value(), "모임의 구성원이 아닙니다."),
    NOT_FOUND_TEAM(5505, HttpStatus.NOT_FOUND.value(), "존재하지 않는 모임입니다."),

    /**
     * 6000: 약속(Plan) 정보 오류
     */
    NOT_WAITING_PLAN(6000, HttpStatus.BAD_REQUEST.value(), "대기 중인 약속 아닙니다."),
    NOT_FIXED_PLAN(6001, HttpStatus.BAD_REQUEST.value(), "확정된 약속이 아닙니다."),
    NOT_PAST_PLAN(6002, HttpStatus.BAD_REQUEST.value(), "지난 약속이 아닙니다."),
    ALREADY_FIXED_PLAN(6003, HttpStatus.BAD_REQUEST.value(), "이미 확정된 약속입니다."),
    DATE_NOT_IN_PERIOD(6004, HttpStatus.BAD_REQUEST.value(), "정해진 기간에서 벗어난 날짜입니다."),

    /**
     * 9000: 기타 오류
     */
    INVALID_STORAGE_DOMAIN(9000, HttpStatus.BAD_REQUEST.value(), "업로드할 이미지의 도메인이 올바르지 않습니다."),
    INVALID_FILE_EXTENSION(9002, HttpStatus.BAD_REQUEST.value(), "파일의 형식이 올바르지 않습니다."),
    NOT_FOUND_FILE(9003, HttpStatus.BAD_REQUEST.value(), "파일이 업로드되지 않았습니다.");

    private final int code;
    private final int status;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
