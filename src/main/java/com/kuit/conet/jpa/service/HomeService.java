package com.kuit.conet.jpa.service;

import com.kuit.conet.annotation.UserId;
import com.kuit.conet.dto.home.HomeFixedPlanOnDayDTO;
import com.kuit.conet.dto.plan.WaitingPlanDTO;
import com.kuit.conet.dto.web.request.plan.HomePlanRequestDTO;
import com.kuit.conet.dto.web.response.plan.HomePlanOnDayResponseDTO;
import com.kuit.conet.dto.web.response.plan.PlanDateOnMonthResponseDTO;
import com.kuit.conet.dto.web.response.plan.WaitingPlanResponseDTO;
import com.kuit.conet.jpa.repository.HomeRepository;
import jakarta.servlet.http.HttpServletRequest;
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
        //TODO: utils/DateFormatValidator 생성 후, request에 날짜가 형식에 맞추어 잘 왔는지 검사하기 (정규표현식으로)
        List<Date> fixedPlansInMonth = homeRepository.getHomeFixedPlansInMonth(userId, homeRequest.getSearchDate());
        List<Integer> planDates = datesToIntegerList(fixedPlansInMonth);

        return new PlanDateOnMonthResponseDTO(planDates);
    }

    public HomePlanOnDayResponseDTO getHomeFixedPlanOnDay(Long userId, HomePlanRequestDTO homeRequest) {
        //TODO: utils/DateFormatValidator 생성 후, request에 날짜가 형식에 맞추어 잘 왔는지 검사하기 (정규표현식으로)
        List<HomeFixedPlanOnDayDTO> plans = homeRepository.getHomeFixedPlansOnDay(userId, homeRequest.getSearchDate());

        return new HomePlanOnDayResponseDTO(plans.size(), plans);
    }

    public WaitingPlanResponseDTO getHomeWaitingPlan(Long userId) {
        List<WaitingPlanDTO> plans = homeRepository.getHomeWaitingPlans(userId);

        return new WaitingPlanResponseDTO(plans);
    }

}
