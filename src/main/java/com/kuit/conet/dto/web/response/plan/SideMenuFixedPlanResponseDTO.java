package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.SideMenuFixedPlanDTO;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class SideMenuFixedPlanResponseDTO {
    int count;
    List<SideMenuFixedPlanDTO> plans;
}