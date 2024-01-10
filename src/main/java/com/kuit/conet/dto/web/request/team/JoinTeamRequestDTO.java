package com.kuit.conet.dto.web.request.team;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JoinTeamRequestDTO {
    private String inviteCode;
}