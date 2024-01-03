package com.kuit.conet.dto.web.response.plan;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlanDateOnMonthResponse {
    int count;
    List<Integer> dates;
}
