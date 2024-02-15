package com.kuit.conet.dto.web.response.team;

import lombok.*;

@Getter
@AllArgsConstructor
public class GetTeamMemberResponseDTO {
    private Long userId;
    private String name;
    private String userImgUrl;
}
