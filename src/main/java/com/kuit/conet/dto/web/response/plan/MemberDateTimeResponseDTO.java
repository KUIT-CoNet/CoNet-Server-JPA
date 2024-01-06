package com.kuit.conet.dto.web.response.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDateTimeResponseDTO {
    private Date date;
    private List<MemberResponseDTO> possibleMember;
}
