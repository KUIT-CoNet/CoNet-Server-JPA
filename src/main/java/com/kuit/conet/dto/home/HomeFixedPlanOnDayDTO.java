package com.kuit.conet.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;

import static com.kuit.conet.utils.DateAndTimeFormatter.*;

@Getter
@AllArgsConstructor
public class HomeFixedPlanOnDayDTO {
    private Long planId;
    private String time; // HH:mm
    private String teamName;
    private String planName;

    public HomeFixedPlanOnDayDTO(Long planId, Time time, String teamName, String planName) {
        this.planId = planId;
        this.time = timeDeleteSeconds(time);
        this.teamName = teamName;
        this.planName = planName;
    }
}