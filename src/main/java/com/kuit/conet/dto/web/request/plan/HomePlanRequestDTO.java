package com.kuit.conet.dto.web.request.plan;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HomePlanRequestDTO {
    private String searchDate;
    // 특정 달의 조회: "yyyy-MM"
    // 특정 날짜 조회: "yyyy-MM-dd"
}