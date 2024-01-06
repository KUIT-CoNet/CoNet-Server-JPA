package com.kuit.conet.dto.web.response.team;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetTeamMemberResponseDTO {
    private Long userId;
    private String name;
    private String userImgUrl;
}
