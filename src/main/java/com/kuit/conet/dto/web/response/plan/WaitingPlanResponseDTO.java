package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.WaitingPlanDTO;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class WaitingPlanResponseDTO {
    private int count;
    private List<WaitingPlanDTO> plans;
}