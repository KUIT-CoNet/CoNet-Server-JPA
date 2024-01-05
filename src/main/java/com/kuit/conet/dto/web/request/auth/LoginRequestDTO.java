package com.kuit.conet.dto.web.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDTO {
    private String idToken;
}

/**
 * id_token Request 객체
 * */