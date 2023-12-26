package com.kuit.conet.dto.request.plan;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePlanRequest {
    private Long planId;
    private String planName;
    private String date; // yyyy-MM-dd
    private String time; // hh:mm
    private List<Long> members; // 구성원 userId

}