package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.domain.plan.HomeFixedPlanOnDay;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class HomePlanOnDayResponse {
    int count;
    List<HomeFixedPlanOnDay> plans;
}