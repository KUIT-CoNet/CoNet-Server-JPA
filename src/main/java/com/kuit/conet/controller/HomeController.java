package com.kuit.conet.controller;

import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.request.PlanRequest;
import com.kuit.conet.dto.response.DayPlanResponse;
import com.kuit.conet.dto.response.MonthPlanResponse;
import com.kuit.conet.dto.response.WaitingPlanResponse;
import com.kuit.conet.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    /**
     * 홈 - 날짜 (dd)
     * */
    @GetMapping("/month")
    public BaseResponse<MonthPlanResponse> getPlanOnMonth(HttpServletRequest httpRequest, @RequestBody @Valid PlanRequest planRequest) {
        MonthPlanResponse response = homeService.getPlanOnMonth(httpRequest, planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * 홈 - 날짜(yyyy-MM-dd) / 시각(hh-mm) / 모임 명 / 약속 명
     * - '나'의 직접적인 참여 여부와 무관
     * */
    @GetMapping("/day")
    public BaseResponse<DayPlanResponse> getPlanOnDay(HttpServletRequest httpRequest, @RequestBody @Valid PlanRequest planRequest) {
        DayPlanResponse response = homeService.getPlanOnDay(httpRequest, planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * 홈 - 날짜(yyyy-MM-dd) / 시각(hh-mm) / 모임 명 / 약속 명
     * - '나'의 직접적인 참여 여부와 무관
     * */
    @GetMapping("/waiting")
    public BaseResponse<WaitingPlanResponse> getWaitingPlan(HttpServletRequest httpRequest) {
        WaitingPlanResponse response = homeService.getWaitingPlanOnDay(httpRequest);
        return new BaseResponse<>(response);
    }
}