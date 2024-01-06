package com.kuit.conet.dto.web.request.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateTeamRequestDTO {
    private Long teamId;
    private String teamName;
}
