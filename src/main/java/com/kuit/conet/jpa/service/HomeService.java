package com.kuit.conet.jpa.service;

import com.kuit.conet.dto.request.plan.HomePlanRequest;
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

    public MonthPlanResponse getPlanInMonth(HttpServletRequest httpRequest, HomePlanRequest planRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        List<Date> fixedPlansInMonth = homeRepository.getPlanInMonth(userId, planRequest.getSearchDate());
        List<Integer> planDates = DateFormatter.datesToIntegerList(fixedPlansInMonth);

        return new MonthPlanResponse(planDates.size(), planDates);
    }
}
