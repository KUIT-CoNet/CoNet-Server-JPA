package com.kuit.conet.dto.web.request.team;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateTeamRequestDTO {
    @NotNull
    private String teamName;
}