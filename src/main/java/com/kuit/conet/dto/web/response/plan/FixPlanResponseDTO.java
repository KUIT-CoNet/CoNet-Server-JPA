package com.kuit.conet.dto.web.response.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;
import java.sql.Time;

@Getter
@AllArgsConstructor
public class FixPlanResponseDTO {
    private String planName;
    private Date fixedDate;
    private Time fixedTime;
    private int memberCount;
}
