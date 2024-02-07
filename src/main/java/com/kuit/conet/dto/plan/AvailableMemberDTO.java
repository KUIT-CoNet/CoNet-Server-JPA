package com.kuit.conet.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class AvailableMemberDTO {
    private int time;
    private int section; //인원수에 따른 구간 (0~3)
    private List<String> memberNames;
    private List<Long> memberIds;
//    private Map<Long, String> members;
}