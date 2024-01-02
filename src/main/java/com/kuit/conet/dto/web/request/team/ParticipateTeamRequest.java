package com.kuit.conet.dto.web.request.team;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParticipateTeamRequest {
    private String inviteCode;
}