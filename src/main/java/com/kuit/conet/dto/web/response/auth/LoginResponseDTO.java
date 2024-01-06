package com.kuit.conet.dto.web.response.auth;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LoginResponseDTO {
    private String email;
    private String accessToken;
    private String refreshToken;
    private Boolean isRegistered;

    public LoginResponseDTO(String email, String accessToken, String refreshToken, Boolean isRegistered) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isRegistered = isRegistered;
    }
}
