package com.kuit.conet.dto.web.request.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinTeamRequestDTO {
    private String inviteCode;
}