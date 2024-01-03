package com.kuit.conet.dto.plan;

import com.kuit.conet.utils.DateFormatter;
import lombok.Getter;

import java.sql.Date;

import static com.kuit.conet.utils.DateFormatter.*;

@Getter
public class WaitingPlan {
    private Long planId;
    private String startDate; // yyyy. MM. dd
    private String endDate; // yyyy. MM. dd
    private String teamName;
    private String planName;

    public WaitingPlan(Long planId, Date startDate, Date endDate, String teamName, String planName) {
        this.planId = planId;
        this.startDate = dateToStringWithDot(startDate);
        this.endDate = dateToStringWithDot(endDate);
        this.teamName = teamName;
        this.planName = planName;
    }
}