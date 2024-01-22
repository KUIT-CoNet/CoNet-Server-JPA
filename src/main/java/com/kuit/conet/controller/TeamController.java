package com.kuit.conet.controller;

import com.kuit.conet.annotation.UserId;
import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.request.team.CreateTeamRequestDTO;
import com.kuit.conet.dto.web.request.team.JoinTeamRequestDTO;
import com.kuit.conet.dto.web.request.team.TeamIdRequestDTO;
import com.kuit.conet.dto.web.request.team.UpdateTeamRequestDTO;
import com.kuit.conet.dto.web.response.StorageImgResponseDTO;
import com.kuit.conet.dto.web.response.team.*;
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
    public BaseResponse<List<GetTeamMemberResponseDTO>> getTeamMembers(@PathVariable @Valid Long teamId, @UserId @Valid Long userId) {
        List<GetTeamMemberResponseDTO> response = teamService.getTeamMembers(teamId, userId);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 모임 상세 조회 api
     */
    @GetMapping("/{teamId}")
    public BaseResponse<GetTeamResponseDTO> getTeamDetail(@PathVariable Long teamId, @UserId Long userId) {
        GetTeamResponseDTO response = teamService.getTeamDetail(teamId, userId);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 모임 수정 api
     */
    @PostMapping("/update")
    public BaseResponse<StorageImgResponseDTO> updateTeam(@RequestPart(value = "request") @Valid UpdateTeamRequestDTO teamRequest, @UserId Long userId, @RequestParam(value = "file") MultipartFile file) {
        StorageImgResponseDTO response = teamService.updateTeam(teamRequest, userId, file);
        return new BaseResponse<StorageImgResponseDTO>(response);
    }

    /**
     * @apiNote 모임 코드 재발급 api
     */
    @PostMapping("/code")
    public BaseResponse<RegenerateCodeResponseDTO> regenerateCode(@RequestBody @Valid TeamIdRequestDTO teamRequest) {
        RegenerateCodeResponseDTO response = teamService.regenerateCode(teamRequest);
        return new BaseResponse<>(response);
    }
}
