package com.kuit.conet.dto.web.request.plan;

import com.kuit.conet.domain.plan.AvailableDateTime;
import lombok.Getter;

import java.util.List;

@Getter
public class RegisterAvailableTimeRequestDTO {
    private Long planId;
    private List<AvailableDateTime> availableDateTimes; // 7개 날짜에 대한
}
