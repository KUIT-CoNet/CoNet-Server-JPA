package com.kuit.conet.domain.plan;

import com.kuit.conet.utils.DateFormatter;
import lombok.Getter;

import java.sql.Date;

@Getter
public class WaitingPlan {
    private Long planId;
    private String startDate; // yyyy. MM. dd
    private String endDate; // yyyy. MM. dd
    private String teamName;
    private String planName;

    public WaitingPlan(Long planId, Date startDate, Date endDate, String teamName, String planName) {
        this.planId = planId;
        this.startDate = DateFormatter.dateToStringWithDot(startDate);
        this.endDate = DateFormatter.dateToStringWithDot(endDate);
        this.teamName = teamName;
        this.planName = planName;
    }
}