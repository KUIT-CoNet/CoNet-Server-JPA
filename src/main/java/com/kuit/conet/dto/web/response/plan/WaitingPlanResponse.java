package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.WaitingPlan;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class WaitingPlanResponse {
    private int count;
    private List<WaitingPlan> plans;
}