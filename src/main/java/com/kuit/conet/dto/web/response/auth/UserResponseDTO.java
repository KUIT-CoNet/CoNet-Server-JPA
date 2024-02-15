package com.kuit.conet.dto.web.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private String platformId;
    private String email;
}