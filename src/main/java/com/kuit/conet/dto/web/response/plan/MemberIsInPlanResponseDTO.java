package com.kuit.conet.dto.web.response.plan;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberIsInPlanResponseDTO {
    private Long userId;
    private String name;
    private String userImgUrl;
    private Boolean isInPlan;

    public MemberIsInPlanResponseDTO(Long userId, String name, String userImgUrl) {
        this.userId = userId;
        this.name = name;
        this.userImgUrl = userImgUrl;
    }
}
