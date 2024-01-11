package com.kuit.conet.dto.web.response.team;

import com.kuit.conet.jpa.domain.team.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateTeamResponseDTO {
    private Long teamId;
    private String inviteCode;

    public CreateTeamResponseDTO(Team team) {
        this.teamId = team.getId();
        this.inviteCode = team.getInviteCode();
    }
}