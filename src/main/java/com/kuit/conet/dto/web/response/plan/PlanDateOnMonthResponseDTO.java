package com.kuit.conet.dto.web.response.plan;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlanDateOnMonthResponseDTO {
    int count;
    List<Integer> dates;
}