package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.UserAvailableTimeDTO;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAvailableTimeResponseDTO {
    private Long planId;
    private Long userId;
    private Boolean hasRegisteredTime; // 가능한 시간을 입력했는지
    private Boolean hasPossibleTime; // 가능한 시간이 존재하는지
    private List<UserAvailableTimeDTO> availableTimes;

    public static UserAvailableTimeResponseDTO notRegistered(Long planId, Long userId) {
        UserAvailableTimeResponseDTO responseDTO = new UserAvailableTimeResponseDTO();
        responseDTO.planId = planId;
        responseDTO.userId = userId;
        responseDTO.hasRegisteredTime = false;
        responseDTO.hasPossibleTime = false;
        responseDTO.availableTimes = null;

        return responseDTO;
    }

    public static UserAvailableTimeResponseDTO registered(Long planId, Long userId, Boolean hasPossibleTime, List<UserAvailableTimeDTO> availableTimes) {
        UserAvailableTimeResponseDTO responseDTO = new UserAvailableTimeResponseDTO();
        responseDTO.planId = planId;
        responseDTO.userId = userId;
        responseDTO.hasRegisteredTime = true;
        responseDTO.hasPossibleTime = hasPossibleTime;
        responseDTO.availableTimes = availableTimes;

        return responseDTO;
    }
}
