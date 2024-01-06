package com.kuit.conet.dto.web.response.team;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetTeamResponseDTO {
    private Long teamId;
    private String teamName;
    private String teamImgUrl;
    private Long teamMemberCount;
    private Boolean isNew;
    private Boolean bookmark;

    public GetTeamResponseDTO(Long teamId, String teamName, String teamImgUrl, Long teamMemberCount) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamImgUrl = teamImgUrl;
        this.teamMemberCount = teamMemberCount;
    }
}