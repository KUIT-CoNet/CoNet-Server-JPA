package com.kuit.conet.dto.web.response.team;

import com.kuit.conet.jpa.domain.team.Team;
import com.kuit.conet.jpa.service.validator.TeamValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTeamResponseDTO {
    private Long teamId;
    private String teamName;
    private String teamImgUrl;
    private Long teamMemberCount;
    private Boolean isNew;
    private Boolean bookmark;


    public GetTeamResponseDTO(Team team, int teamMemberCount, Boolean bookmark) {
        this.teamId = team.getId();
        this.teamName = team.getName();
        this.teamImgUrl = team.getImgUrl();
        this.teamMemberCount = (long) teamMemberCount;
        this.bookmark = bookmark;

        //isNew 결정
        this.isNew = TeamValidator.isNewTeam(team);
    }
}