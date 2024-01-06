package com.kuit.conet.dto.web.response.plan;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPossibleTimeResponseDTO {
    private Date date;
    private List<Integer> time;
}
