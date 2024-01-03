package com.kuit.conet.controller;

import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.request.plan.HomePlanRequest;
import com.kuit.conet.dto.web.response.plan.HomePlanOnDayResponse;
import com.kuit.conet.dto.web.response.plan.PlanDateOnMonthResponse;
import com.kuit.conet.dto.web.response.plan.WaitingPlanResponse;
import com.kuit.conet.jpa.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/plan")
public class HomeController {
    private final HomeService homeService;

    /**
     * 홈 특정 달의 확정 약속이 존재하는 날짜 조회 - 날짜 (dd)
     * */
    @GetMapping("/month")
    public BaseResponse<PlanDateOnMonthResponse> getHomeFixedPlanInMonth(HttpServletRequest httpRequest, @ModelAttribute @Valid HomePlanRequest homeRequest) {
        PlanDateOnMonthResponse response = homeService.getHomeFixedPlanInMonth(httpRequest, homeRequest);
        return new BaseResponse<>(response);
    }

    /**
     * 홈 특정 날짜의 확정 약속 조회 - 날짜(yyyy-MM-dd) / 시각(hh-mm) / 모임 명 / 약속 명
     * - '나'의 직접적인 참여 여부와 무관
     * */
    @GetMapping("/day")
    public BaseResponse<HomePlanOnDayResponse> getHomeFixedPlanOnDay(HttpServletRequest httpRequest, @ModelAttribute @Valid HomePlanRequest homeRequest) {
        HomePlanOnDayResponse response = homeService.getHomeFixedPlanOnDay(httpRequest, homeRequest);
        return new BaseResponse<>(response);
    }

    /**
     * 홈 대기 중인 약속 조회 - 날짜(yyyy-MM-dd) / 시각(hh-mm) / 모임 명 / 약속 명
     * - '나'의 직접적인 참여 여부와 무관
     * */
    @GetMapping("/waiting")
    public BaseResponse<WaitingPlanResponse> getHomeWaitingPlan(HttpServletRequest httpRequest) {
        WaitingPlanResponse response = homeService.getHomeWaitingPlan(httpRequest);
        return new BaseResponse<>(response);
    }
}