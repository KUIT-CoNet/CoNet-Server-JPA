package com.kuit.conet.dto.web.request.plan;

import com.kuit.conet.jpa.domain.plan.PlanPeriod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamFixedPlanInPeriodRequest {
    private Long teamId;
    private PlanPeriod period;

}