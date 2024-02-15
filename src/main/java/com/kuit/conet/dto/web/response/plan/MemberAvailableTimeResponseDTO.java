package com.kuit.conet.dto.web.response.plan;

import com.kuit.conet.dto.plan.MemberDateTimeDTO;
import com.kuit.conet.domain.plan.Plan;
import lombok.Getter;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Getter
public class MemberAvailableTimeResponseDTO {
    private Long teamId;
    private Long planId;
    private String planName;
    private Date planStartPeriod;
    private Date planEndPeriod;
    private Map<Integer, Long> endNumberForEachSection; // 각 구간의 최대 인원 수
    private List<MemberDateTimeDTO> availableMemberDateTime;

    public MemberAvailableTimeResponseDTO(Long teamId, Plan plan, Map<Integer, Long> endNumberForEachSection, List<MemberDateTimeDTO> availableMemberDateTime) {
        this.teamId = teamId;
        this.planId = plan.getId();
        this.planName = plan.getName();
        this.planStartPeriod = plan.getStartPeriod();
        this.planEndPeriod = plan.getEndPeriod();
        this.endNumberForEachSection = endNumberForEachSection;
        this.availableMemberDateTime = availableMemberDateTime;
    }
}
