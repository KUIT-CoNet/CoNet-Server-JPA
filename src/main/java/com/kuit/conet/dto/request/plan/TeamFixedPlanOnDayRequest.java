package com.kuit.conet.dto.request.plan;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamFixedPlanOnDayRequest {
    private Long teamId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date searchDate; // String to Date
    // TODO: 프론트에 변경 사항 알리기
    // 특정 날짜 조회: "yyyy-MM-dd"
}