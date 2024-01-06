package com.kuit.conet.dto.web.request.plan;

import com.kuit.conet.domain.plan.PossibleDateTime;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class PossibleTimeRequestDTO {
    private Long planId;
    private List<PossibleDateTime> possibleDateTimes; // 7개 날짜에 대한
}
