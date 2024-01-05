package com.kuit.conet.domain.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;

import static com.kuit.conet.utils.DateFormatter.*;

@Getter
@AllArgsConstructor
public class HomeFixedPlanOnDay {
    private Long planId;
    private String time; // HH:mm
    private String teamName;
    private String planName;

    public HomeFixedPlanOnDay(Long planId, Time time, String teamName, String planName) {
        this.planId = planId;
        this.time = timeDeleteSeconds(time);
        this.teamName = teamName;
        this.planName = planName;
    }
}