package com.kuit.conet.dto.web.request.plan;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamFixedPlanRequest {
    private Long teamId;
    private String searchDate;
    // 특정 날짜 조회: "yyyy-MM-dd"
    // 특정 달 조회: "yyyy-MM"
}