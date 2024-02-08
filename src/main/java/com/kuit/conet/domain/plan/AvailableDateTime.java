package com.kuit.conet.domain.plan;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
public class AvailableDateTime {
    private Date date;
    private List<Integer> time;
}
