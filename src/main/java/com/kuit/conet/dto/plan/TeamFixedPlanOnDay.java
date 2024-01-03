package com.kuit.conet.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamFixedPlanOnDay {
    private Long planId;
    private String planName;
    private Time time; // hh:mm
}