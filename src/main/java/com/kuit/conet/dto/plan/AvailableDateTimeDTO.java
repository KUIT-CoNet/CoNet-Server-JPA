package com.kuit.conet.dto.plan;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
public class AvailableDateTimeDTO {
    private Date date;
    private List<Integer> time;
}
