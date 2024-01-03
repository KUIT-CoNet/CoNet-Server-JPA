package com.kuit.conet.domain.plan;

import com.kuit.conet.utils.DateFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;

@Getter
@AllArgsConstructor
public class HomeFixedPlanOnDay {
    private Long planId;
    private String time; // HH:mm
    private String teamName;
    private String planName;

    public HomeFixedPlanOnDay(Long planId, Time time, String teamName, String planName) {
        this.planId = planId;
        this.time = DateFormatter.timeDeleteSecondsAndToString(time);
        this.teamName = teamName;
        this.planName = planName;
    }
}