package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.FixedPlanOnDay;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamPlanOnDayResponse {
    int count;
    List<FixedPlanOnDay> plans;
}