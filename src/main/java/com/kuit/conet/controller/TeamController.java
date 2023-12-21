package com.kuit.conet.controller;

import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.request.team.CreateTeamRequest;
import com.kuit.conet.dto.request.team.ParticipateTeamRequest;
import com.kuit.conet.dto.request.team.TeamIdRequest;
import com.kuit.conet.dto.request.team.UpdateTeamRequest;
import com.kuit.conet.dto.response.StorageImgResponse;
import com.kuit.conet.dto.response.team.*;
import com.kuit.conet.jpa.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/create")
    public BaseResponse<CreateTeamResponse> createTeam(@RequestPart(value = "request") @Valid CreateTeamRequest createTeamRequest, HttpServletRequest httpRequest, @RequestParam(value = "file") MultipartFile file) {
        CreateTeamResponse response = teamService.createTeam(createTeamRequest, httpRequest, file);
        return new BaseResponse<CreateTeamResponse>(response);
    }
}
