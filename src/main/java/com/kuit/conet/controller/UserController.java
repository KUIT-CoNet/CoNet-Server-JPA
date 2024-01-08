package com.kuit.conet.controller;

import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.response.StorageImgResponseDTO;
import com.kuit.conet.jpa.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final MemberService memberService;

/*    @PostMapping("/delete")
    public BaseResponse<String> userDelete(HttpServletRequest httpRequest) {
        memberService.userDelete(httpRequest);
        return new BaseResponse<>("유저 탈퇴에 성공하였습니다.");
    }

    @GetMapping
    public BaseResponse<UserResponseDTO> getUser(HttpServletRequest httpRequest) {
        UserResponseDTO response = memberService.getUser(httpRequest);
        return new BaseResponse<>(response);
    }*/

    @PostMapping("/image")
    public BaseResponse<StorageImgResponseDTO> updateImg(HttpServletRequest httpRequest, @RequestParam(value = "file") MultipartFile file) {
        StorageImgResponseDTO response = memberService.updateImg(httpRequest, file);
        return new BaseResponse<>(response);
    }

/*    @PostMapping("/name")
    public BaseResponse<String> updateName(HttpServletRequest httpRequest, @RequestBody @Valid NameRequestDTO nameRequest) {
        memberService.updateName(httpRequest, nameRequest);
        return new BaseResponse<>("이름 변경에 성공하였습니다.");
    }*/
}