package com.kuit.conet.dto.plan;

import com.kuit.conet.utils.DateFormatter;
import lombok.Getter;

import java.sql.Time;

@Getter
public class FixedPlanOnDay {
    private Long planId;
    private String planName;
    private String time; // HH:mm

    public FixedPlanOnDay(Long planId, String planName, Time time) {
        this.planId = planId;
        this.planName = planName;
        this.time = DateFormatter.timeDeleteSecondsAndToString(time);
    }
}