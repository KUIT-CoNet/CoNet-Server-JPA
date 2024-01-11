package com.kuit.conet.controller;

import com.kuit.conet.annotation.UserId;
import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.request.team.CreateTeamRequestDTO;
import com.kuit.conet.dto.web.request.team.JoinTeamRequestDTO;
import com.kuit.conet.dto.web.request.team.TeamIdRequestDTO;
import com.kuit.conet.dto.web.response.team.CreateTeamResponseDTO;
import com.kuit.conet.dto.web.response.team.GetTeamMemberResponseDTO;
import com.kuit.conet.dto.web.response.team.GetTeamResponseDTO;
import com.kuit.conet.dto.web.response.team.JoinTeamResponseDTO;
import com.kuit.conet.jpa.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    /**
     * @apiNote 모임 생성 api
     */
    @PostMapping
    public BaseResponse<CreateTeamResponseDTO> createTeam(@RequestPart(value = "request") @Valid CreateTeamRequestDTO teamRequest, @UserId Long userId, @RequestParam(value = "file") MultipartFile file) {
        CreateTeamResponseDTO response = teamService.createTeam(teamRequest, userId, file);
        return new BaseResponse<CreateTeamResponseDTO>(response);
    }

    /**
     * @apiNote 모임 참가 api
     */
    @PostMapping("/join")
    public BaseResponse<JoinTeamResponseDTO> joinTeam(@RequestBody @Valid JoinTeamRequestDTO teamRequest, @UserId Long userId) {
        JoinTeamResponseDTO response = teamService.joinTeam(teamRequest, userId);
        return new BaseResponse<JoinTeamResponseDTO>(response);
    }

    /**
     * @apiNote 모임 리스트 조회 api
     */
    @GetMapping
    public BaseResponse<List<GetTeamResponseDTO>> getTeam(@UserId Long userId) {
        List<GetTeamResponseDTO> responses = teamService.getTeam(userId);
        return new BaseResponse<List<GetTeamResponseDTO>>(responses);
    }

    /**
     * @apiNote 모임 탈퇴 api
     */
    @PostMapping("/leave")
    public BaseResponse<String> leaveTeam(@RequestBody @Valid TeamIdRequestDTO teamRequest, @UserId Long userId) {
        String response = teamService.leaveTeam(teamRequest, userId);
        return new BaseResponse<String>(response);
    }

    /**
     * @apiNote 모임 삭제 api
     */
    @DeleteMapping("/{teamId}")
    public BaseResponse<String> deleteTeam(@PathVariable Long teamId, @UserId Long userId) {
        String response = teamService.deleteTeam(teamId, userId);
        return new BaseResponse<String>(response);
    }

    /**
     * @apiNote 모임 구성원 조회 api
     */
    @GetMapping("/{teamId}/members")
    public BaseResponse<List<GetTeamMemberResponseDTO>> getTeamMembers(@PathVariable @Valid Long teamId, @UserId Long userId) {
        List<GetTeamMemberResponseDTO> response = teamService.getTeamMembers(teamId, userId);
        return new BaseResponse<>(response);
    }

    /*

    @PostMapping("/code")
    public BaseResponse<RegenerateCodeResponse> regenerateCode(@RequestBody @Valid TeamIdRequest request) {
        RegenerateCodeResponse response = teamService.regenerateCode(request);
        return new BaseResponse<>(response);
    }


    @PostMapping("/update")
    public BaseResponse<StorageImgResponse> updateTeam(@RequestPart(value = "request") @Valid UpdateTeamRequest updateTeamRequest, @RequestParam(value = "file") MultipartFile file) {
        StorageImgResponse response = teamService.updateTeam(updateTeamRequest, file);
        return new BaseResponse<StorageImgResponse>(response);
    }

    @PostMapping("/bookmark")
    public BaseResponse<String> bookmarkTeam(HttpServletRequest httpRequest, @RequestBody @Valid TeamIdRequest request) {
        teamService.bookmarkTeam(httpRequest, request);
        return new BaseResponse<>("모임을 즐겨찾기에 추가하였습니다.");
    }

    @PostMapping("/bookmark/delete")
    public BaseResponse<String> unBookmarkTeam(HttpServletRequest httpRequest, @RequestBody @Valid TeamIdRequest request) {
        teamService.unBookmarkTeam(httpRequest, request);
        return new BaseResponse<>("모임을 즐겨찾기에서 삭제하였습니다.");
    }

    @GetMapping("/detail")
    public BaseResponse<GetTeamResponse> getTeamDetail(HttpServletRequest httpRequest, @ModelAttribute @Valid TeamIdRequest request) {
        GetTeamResponse response = teamService.getTeamDetail(httpRequest, request);
        return new BaseResponse<>(response);
    }

    @GetMapping("/bookmark")
    public BaseResponse<List<GetTeamResponse>> getBookmark(HttpServletRequest httpRequest) {
        List<GetTeamResponse> responses = teamService.getBookmarks(httpRequest);
        return new BaseResponse<>(responses);
    }*/
}
