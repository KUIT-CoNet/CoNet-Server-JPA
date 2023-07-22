package com.kuit.conet.dto.response.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberResponse {
    private int time;
    private int section;
    private String memberName;
}