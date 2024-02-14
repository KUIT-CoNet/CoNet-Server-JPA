package com.kuit.conet.dto.web.request.home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HomePlanRequestDTO {
    private String searchDate;
    // 특정 달의 조회: "yyyy-MM"
    // 특정 날짜 조회: "yyyy-MM-dd"
}