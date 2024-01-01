package com.kuit.conet.jpa.service;

import com.kuit.conet.domain.plan.HomeFixedPlanOnDay;
import com.kuit.conet.dto.request.plan.HomePlanRequest;
import com.kuit.conet.dto.response.plan.HomePlanOnDayResponse;
import com.kuit.conet.dto.response.plan.MonthPlanResponse;
import com.kuit.conet.jpa.repository.HomeRepository;
import com.kuit.conet.utils.DateFormatter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {
    private final HomeRepository homeRepository;

    public MonthPlanResponse getHomeFixedPlanInMonth(HttpServletRequest httpRequest, HomePlanRequest planRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        List<Date> fixedPlansInMonth = homeRepository.getHomeFixedPlansInMonth(userId, planRequest.getSearchDate());
        List<Integer> planDates = DateFormatter.datesToIntegerList(fixedPlansInMonth);

        return new MonthPlanResponse(planDates.size(), planDates);
    }

    public HomePlanOnDayResponse getHomeFixedPlanOnDay(HttpServletRequest httpRequest, HomePlanRequest planRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        List<HomeFixedPlanOnDay> plans = homeRepository.getHomeFixedPlansOnDay(userId, planRequest.getSearchDate());

        return new HomePlanOnDayResponse(plans.size(), plans);
    }

}
