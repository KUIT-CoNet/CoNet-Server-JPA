package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.FixedPlanOnDayDTO;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamPlanOnDayResponseDTO {
    int count;
    List<FixedPlanOnDayDTO> plans;
}