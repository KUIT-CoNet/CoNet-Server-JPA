package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.UserAvailableTimeDTO;
import lombok.Getter;

import java.util.List;

import static com.kuit.conet.domain.plan.AvailableTimeRegisteredStatus.*;

@Getter
public class UserAvailableTimeResponseDTO {
    private Long planId;
    private Long userId;
    private int availableTimeRegisteredStatus;
    private List<UserAvailableTimeDTO> timeSlot;

    public static UserAvailableTimeResponseDTO notRegistered(Long planId, Long userId) {
        UserAvailableTimeResponseDTO responseDTO = new UserAvailableTimeResponseDTO();
        responseDTO.planId = planId;
        responseDTO.userId = userId;
        responseDTO.availableTimeRegisteredStatus = NOT_REGISTERED.getStatus();
        responseDTO.timeSlot = null;

        return responseDTO;
    }

    public static UserAvailableTimeResponseDTO registered(Long planId, Long userId, Boolean hasAvailableTime, List<UserAvailableTimeDTO> timeSlot) {
        UserAvailableTimeResponseDTO responseDTO = new UserAvailableTimeResponseDTO();
        responseDTO.planId = planId;
        responseDTO.userId = userId;
        if (!hasAvailableTime) {
            responseDTO.availableTimeRegisteredStatus = NO_AVAILABLE_TIME.getStatus();
        }
        if (hasAvailableTime) {
            responseDTO.availableTimeRegisteredStatus = HAS_AVAILABLE_TIME.getStatus();
        }
        responseDTO.timeSlot = timeSlot;

        return responseDTO;
    }
}
