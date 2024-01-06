package com.kuit.conet.dto.web.response.plan;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTimeResponseDTO {
    private Long planId;
    private Long userId;
    private Boolean hasRegisteredTime;
    private Boolean hasPossibleTime;
    private List<UserPossibleTimeResponseDTO> possibleTime;
}
