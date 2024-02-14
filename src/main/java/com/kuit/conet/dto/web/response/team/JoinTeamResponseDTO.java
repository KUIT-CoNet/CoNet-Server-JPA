package com.kuit.conet.dto.web.response.team;

import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.team.Team;
import lombok.*;

@Getter
public class JoinTeamResponseDTO {
    private String userName;
    private String teamName;

    public JoinTeamResponseDTO(Team team, Member member) {
        this.userName = member.getName();
        this.teamName = team.getName();
    }
}
