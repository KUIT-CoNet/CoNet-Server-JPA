package com.kuit.conet.jpa.domain.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PlanStatus {
    FIXED("FIXED"),   // 확정
    WAITING("WAITING");   // 대기

    private String planStatus;
}
