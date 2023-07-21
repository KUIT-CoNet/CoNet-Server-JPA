package com.kuit.conet.dto.response.plan;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PossibleTimeResponse {
    private Date date;
    private String time;
}
