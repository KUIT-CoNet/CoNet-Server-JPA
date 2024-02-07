package com.kuit.conet.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class MemberDateTimeDTO {
    private Date date;
    private List<AvailableMemberDTO> sectionAndAvailableTimes;
}
