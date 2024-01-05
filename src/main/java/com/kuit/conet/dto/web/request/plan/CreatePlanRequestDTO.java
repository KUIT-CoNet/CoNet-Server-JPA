package com.kuit.conet.dto.web.request.plan;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
public class CreatePlanRequestDTO {
    private Long teamId;
    private String planName;
    private Date planStartDate;
}