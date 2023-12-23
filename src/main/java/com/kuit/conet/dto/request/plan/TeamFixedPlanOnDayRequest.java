package com.kuit.conet.dto.request.plan;

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
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date searchDate; // String to Date
    // TODO: 프론트에 변경 사항 알리기
    // 특정 달의 조회: "yyyy-MM-01"
    // 특정 날짜 조회: "yyyy-MM-dd"
}