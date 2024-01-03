package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.SideMenuFixedPlan;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class SideMenuFixedPlanResponse {
    int count;
    List<SideMenuFixedPlan> plans;
}