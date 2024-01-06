package com.kuit.conet.controller;

import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.request.plan.HomePlanRequestDTO;
import com.kuit.conet.dto.web.response.plan.HomePlanOnDayResponseDTO;
import com.kuit.conet.dto.web.response.plan.PlanDateOnMonthResponseDTO;
import com.kuit.conet.dto.web.response.plan.WaitingPlanResponseDTO;
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
     * @apiNote 홈 특정 달의 확정 약속이 존재하는 날짜 조회 api
     * / 날짜 (dd)
     * */
    @GetMapping("/month")
    public BaseResponse<PlanDateOnMonthResponseDTO> getHomeFixedPlanInMonth(HttpServletRequest httpRequest, @ModelAttribute @Valid HomePlanRequestDTO homeRequest) {
        PlanDateOnMonthResponseDTO response = homeService.getHomeFixedPlanInMonth(httpRequest, homeRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 홈 특정 날짜의 확정 약속 조회 api
     * / 날짜(yyyy. MM. dd) / 시각(HH:mm) / 모임 명 / 약속 명
     * / '나'의 직접적인 참여 여부와 무관
     * */
    //TODO: 내가 참여하는 약속만 조회
    @GetMapping("/day")
    public BaseResponse<HomePlanOnDayResponseDTO> getHomeFixedPlanOnDay(HttpServletRequest httpRequest, @ModelAttribute @Valid HomePlanRequestDTO homeRequest) {
        HomePlanOnDayResponseDTO response = homeService.getHomeFixedPlanOnDay(httpRequest, homeRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 홈 대기 중인 약속 조회 api
     * / 날짜(yyyy. MM. dd) / 시각(HH:mm) / 모임 명 / 약속 명
     * / '나'의 직접적인 참여 여부와 무관
     * */
    @GetMapping("/waiting")
    public BaseResponse<WaitingPlanResponseDTO> getHomeWaitingPlan(HttpServletRequest httpRequest) {
        WaitingPlanResponseDTO response = homeService.getHomeWaitingPlan(httpRequest);
        return new BaseResponse<>(response);
    }
}