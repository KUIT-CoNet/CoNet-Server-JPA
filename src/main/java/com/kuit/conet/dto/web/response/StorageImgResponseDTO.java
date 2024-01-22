package com.kuit.conet.dto.web.response;

import com.kuit.conet.jpa.domain.team.Team;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StorageImgResponseDTO {
    private String name;
    private String imgUrl;

    public StorageImgResponseDTO(Team team) {
        this.name = team.getName();
        this.imgUrl = team.getImgUrl();
    }
}