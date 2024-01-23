package com.kuit.conet.controller;

import com.kuit.conet.annotation.UserId;
import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.request.member.NameRequestDTO;
import com.kuit.conet.dto.web.request.team.TeamIdRequestDTO;
import com.kuit.conet.dto.web.response.StorageImgResponseDTO;
import com.kuit.conet.dto.web.response.member.MemberResponseDTO;
import com.kuit.conet.dto.web.response.team.GetTeamResponseDTO;
import com.kuit.conet.jpa.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

/*    @PostMapping("/delete")
    public BaseResponse<String> userDelete(@UserId Long userId) {
        memberService.userDelete(userId);
        return new BaseResponse<>("유저 탈퇴에 성공하였습니다.");
    }*/

    @GetMapping
    public BaseResponse<MemberResponseDTO> getUser(@UserId Long userId) {
        MemberResponseDTO response = memberService.getUser(userId);
        return new BaseResponse<>(response);
    }

    @PostMapping("/image")
    public BaseResponse<StorageImgResponseDTO> updateImg(@UserId Long userId, @RequestParam(value = "file") MultipartFile file) {
        StorageImgResponseDTO response = memberService.updateImg(userId, file);
        return new BaseResponse<>(response);
    }

    @PostMapping("/name")
    public BaseResponse<String> updateName(@UserId Long userId, @RequestBody @Valid NameRequestDTO nameRequest) {
        memberService.updateName(userId, nameRequest);
        return new BaseResponse<>("이름 변경에 성공하였습니다.");
    }

    @GetMapping("/bookmark")
    public BaseResponse<List<GetTeamResponseDTO>> getBookmark(@UserId Long userId) {
        List<GetTeamResponseDTO> responses = memberService.getBookmarks(userId);
        return new BaseResponse<>(responses);
    }

    @PostMapping("/bookmark")
    public BaseResponse<String> bookmarkTeam(@UserId Long userId, @RequestBody @Valid TeamIdRequestDTO request) {
        return new BaseResponse<>(memberService.bookmarkTeam(userId, request));
    }
}