package com.kuit.conet.controller;

import com.kuit.conet.annotation.ClientIp;
import com.kuit.conet.annotation.UserId;
import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.request.auth.LoginRequestDTO;
import com.kuit.conet.dto.web.request.auth.PutOptionTermAndNameRequestDTO;
import com.kuit.conet.dto.web.response.auth.TermAndNameResponseDTO;
import com.kuit.conet.dto.web.response.auth.LoginResponseDTO;
import com.kuit.conet.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    /**
     * @apiNote 애플 로그인 api
     * */
    @PostMapping("/login/apple")
    public BaseResponse<LoginResponseDTO> loginApple(@RequestBody @Valid LoginRequestDTO loginRequest, @ClientIp String clientIp) {
        LoginResponseDTO response = authService.appleLogin(loginRequest, clientIp);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 카카오 로그인 api
     * */
    @PostMapping("/login/kakao")
    public BaseResponse<LoginResponseDTO> loginKakao(@RequestBody @Valid LoginRequestDTO loginRequest, @ClientIp String clientIp) {
        LoginResponseDTO response = authService.kakaoLogin(loginRequest, clientIp);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 이용 약관 동의 및 이름 입력 api
     * */
    @PostMapping("/term-and-name")
    public BaseResponse<TermAndNameResponseDTO> agreeTermAndPutName(@UserId Long userId, @RequestBody @Valid PutOptionTermAndNameRequestDTO authRequest) {
        TermAndNameResponseDTO response = authService.agreeTermAndPutName(userId, authRequest);
        return new BaseResponse<>(response);
    }

    @PostMapping("/regenerate-token")
    public BaseResponse<LoginResponseDTO> regenerateToken(HttpServletRequest httpRequest, @ClientIp String clientIp) {
        LoginResponseDTO response = authService.regenerateToken((String) httpRequest.getAttribute("token"), clientIp);
        return new BaseResponse<>(response);
    }

}