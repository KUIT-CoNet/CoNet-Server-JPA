package com.kuit.conet.jpa.service;

import com.kuit.conet.dto.request.plan.HomePlanRequest;
import com.kuit.conet.dto.response.plan.MonthPlanResponse;
import com.kuit.conet.jpa.repository.HomeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {
    private final HomeRepository homeRepository;

    public MonthPlanResponse getPlanInMonth(HttpServletRequest httpRequest, HomePlanRequest planRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));

        List<Date> dateList = homeRepository.getPlanInMonth(userId, planRequest.getSearchDate());

        return getMonthPlanResponse(dateList);
    }

    // Response 만드는 걸 목적..? 으로 하는 메서드
    private MonthPlanResponse getMonthPlanResponse(List<Date> dateList) {
        List<Integer> planDates = dateList.stream()
                .map(tempDate -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(tempDate);
                }) // Date -> String
                .map(tempDate -> Integer.parseInt(tempDate.split("-")[2]))
                .toList();

        return new MonthPlanResponse(planDates.size(), planDates);
    }
}
