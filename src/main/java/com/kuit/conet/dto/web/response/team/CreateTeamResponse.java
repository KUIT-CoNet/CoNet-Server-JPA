package com.kuit.conet.dto.web.response.team;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateTeamResponse {
    private Long teamId;
    private String inviteCode;
}