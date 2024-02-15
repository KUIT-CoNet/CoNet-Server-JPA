package com.kuit.conet.dto.plan;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class OneMemberAvailableTimeDTO {
    private Date date;
    private List<Integer> availableTimes;

    public OneMemberAvailableTimeDTO(Date date) {
        this.date = date;
    }

    public void setAvailableTimes(List<Integer> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
