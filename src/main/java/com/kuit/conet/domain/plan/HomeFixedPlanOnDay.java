package com.kuit.conet.domain.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeFixedPlanOnDay {
    private Long planId;
    private Time time; // hh:mm:ss
    private String teamName;
    private String planName;
}