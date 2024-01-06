package com.kuit.conet.dto.web.request.plan;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateWaitingPlanRequestDTO {
    private Long planId;
    private String planName;
}
