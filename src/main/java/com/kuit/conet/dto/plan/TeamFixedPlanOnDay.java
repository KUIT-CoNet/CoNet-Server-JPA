package com.kuit.conet.dto.plan;

import com.kuit.conet.utils.DateFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
public class TeamFixedPlanOnDay {
    private Long planId;
    private String planName;
    private String time; // hh:mm

    public TeamFixedPlanOnDay(Long planId, String planName, Time time) {
        this.planId = planId;
        this.planName = planName;
        this.time = DateFormatter.timeDeleteSecondsAndToString(time);
    }
}