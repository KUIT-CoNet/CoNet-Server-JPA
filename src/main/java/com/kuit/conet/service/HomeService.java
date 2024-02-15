package com.kuit.conet.service;

import com.kuit.conet.dto.home.HomeFixedPlanOnDayDTO;
import com.kuit.conet.dto.plan.WaitingPlanDTO;
import com.kuit.conet.dto.web.request.home.HomePlanRequestDTO;
import com.kuit.conet.dto.web.response.plan.HomePlanOnDayResponseDTO;
import com.kuit.conet.dto.web.response.plan.PlanDateOnMonthResponseDTO;
import com.kuit.conet.dto.web.response.plan.WaitingPlanResponseDTO;
import com.kuit.conet.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

import static com.kuit.conet.utils.DateAndTimeFormatter.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {
    private final HomeRepository homeRepository;

    public PlanDateOnMonthResponseDTO getHomeFixedPlanInMonth(Long userId, HomePlanRequestDTO homeRequest) {
        List<Date> fixedPlansInMonth = homeRepository.getHomeFixedPlansInMonth(userId, homeRequest.getSearchDate());
        List<Integer> planDates = datesToIntegerList(fixedPlansInMonth);

        return new PlanDateOnMonthResponseDTO(planDates);
    }

    public HomePlanOnDayResponseDTO getHomeFixedPlanOnDay(Long userId, HomePlanRequestDTO homeRequest) {
        List<HomeFixedPlanOnDayDTO> plans = homeRepository.getHomeFixedPlansOnDay(userId, homeRequest.getSearchDate());

        return new HomePlanOnDayResponseDTO(plans.size(), plans);
    }

    public WaitingPlanResponseDTO getHomeWaitingPlan(Long userId) {
        List<WaitingPlanDTO> homeWaitingPlans = homeRepository.getHomeWaitingPlans(userId);

        return new WaitingPlanResponseDTO(homeWaitingPlans);
    }

}
