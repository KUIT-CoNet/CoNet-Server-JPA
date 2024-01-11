package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.UserAvailableTimeDTO;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserAvailableTimeResponseDTO {
    private Long planId;
    private Long userId;
    private Boolean hasRegisteredTime; // 가능한 시간을 입력했는지
    private Boolean hasAvailableTime; // 가능한 시간이 존재하는지
    private List<UserAvailableTimeDTO> timeSlot;

    public static UserAvailableTimeResponseDTO notRegistered(Long planId, Long userId) {
        UserAvailableTimeResponseDTO responseDTO = new UserAvailableTimeResponseDTO();
        responseDTO.planId = planId;
        responseDTO.userId = userId;
        responseDTO.hasRegisteredTime = false;
        responseDTO.hasAvailableTime = false;
        responseDTO.timeSlot = null;

        return responseDTO;
    }

    public static UserAvailableTimeResponseDTO registered(Long planId, Long userId, Boolean hasAvailableTime, List<UserAvailableTimeDTO> timeSlot) {
        UserAvailableTimeResponseDTO responseDTO = new UserAvailableTimeResponseDTO();
        responseDTO.planId = planId;
        responseDTO.userId = userId;
        responseDTO.hasRegisteredTime = true;
        responseDTO.hasAvailableTime = hasAvailableTime;
        responseDTO.timeSlot = timeSlot;

        return responseDTO;
    }
}
