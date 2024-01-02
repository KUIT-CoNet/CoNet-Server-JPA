package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.TeamFixedPlanOnDay;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamPlanOnDayResponse {
    int count;
    List<TeamFixedPlanOnDay> plans;
}