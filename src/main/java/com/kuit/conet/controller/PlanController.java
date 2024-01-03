package com.kuit.conet.controller;

import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.request.plan.*;
import com.kuit.conet.dto.web.response.plan.*;
import com.kuit.conet.jpa.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;

    /**
     * @apiNote 약속 생성 api
     * */
    @PostMapping
    public BaseResponse<CreatePlanResponse> createPlan(@RequestBody @Valid CreatePlanRequest planRequest) {
        CreatePlanResponse response = planService.createPlan(planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 모임 내 특정 날짜 확정 약속 조회 api
     * / '나'의 직접적인 참여 여부와 무관
     * */
    @GetMapping("/day")
    public BaseResponse<TeamPlanOnDayResponse> getFixedPlanOnDay(@ModelAttribute @Valid TeamFixedPlanRequest planRequest) {
        TeamPlanOnDayResponse response = planService.getFixedPlanOnDay(planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote  모임 내 특정 달의 약속이 존재하는 날짜 조회 api
     */
    @GetMapping("/month")
    public BaseResponse<PlanDateOnMonthResponse> getFixedPlanInMonth(@ModelAttribute @Valid TeamFixedPlanRequest planRequest) {
        PlanDateOnMonthResponse response = planService.getFixedPlanInMonth(planRequest);
        return new BaseResponse<>(response);
    }

/*
    @PostMapping("/time")
    public BaseResponse<String> registerTime(HttpServletRequest httpRequest, @RequestBody @Valid PossibleTimeRequest request) {
        planService.saveTime(request, httpRequest);
        return new BaseResponse<>("사용자의 가능한 시간 등록에 성공하였습니다.");
    }

    @GetMapping("/user-time")
    public BaseResponse<UserTimeResponse> getUserTime(HttpServletRequest httpRequest, @ModelAttribute @Valid PlanIdRequest request) {
        UserTimeResponse response = planService.getUserTime(request, httpRequest);
        return new BaseResponse<>(response);
    }

    @GetMapping("/member-time")
    public BaseResponse<MemberPossibleTimeResponse> getMemberTime(@ModelAttribute @Valid PlanIdRequest request) {
        MemberPossibleTimeResponse response = planService.getMemberTime(request);
        return new BaseResponse<>(response);
    }

    @PostMapping("/fix")
    public BaseResponse<String> fixPlan(@RequestBody @Valid FixPlanRequest fixPlanRequest) {
        String response = planService.fixPlan(fixPlanRequest);
        return new BaseResponse<>(response);
    }

    *//**
     * 약속 상세 정보 조회
     * *//*
    @GetMapping("/detail")
    public BaseResponse<PlanDetail> getPlanDetail(@ModelAttribute @Valid PlanIdRequest planRequest) {
        PlanDetail response = planService.getPlanDetail(planRequest);
        return new BaseResponse<>(response);
    }

    @PostMapping("/delete")
    public BaseResponse<String> deletePlan(@RequestBody @Valid PlanIdRequest planRequest) {
        String response = planService.deletePlan(planRequest);
        return new BaseResponse<>(response);
    }

    @PostMapping("/update-waiting")
    public BaseResponse<String> updateWaitingPlan(@RequestBody @Valid UpdateWaitingPlanRequest planRequest) {
        String response = planService.updateWaitingPlan(planRequest);
        return new BaseResponse<>(response);
    }

    @PostMapping("/update-fixed")
    //TODO: history 관련 내용 삭제
    public BaseResponse<String> updateFixedPlan(@RequestPart(value = "requestBody") @Valid UpdatePlanRequest planRequest, @RequestPart(value = "file", required = false) MultipartFile historyImg) {
        String response = planService.updateFixedPlan(planRequest, historyImg);
        return new BaseResponse<>(response);
    }

    *//**
     * 지난 약속 - 모임 내 사이드바 메뉴
     * *//*
    @GetMapping("/past")
    public BaseResponse<List<PastPlan>> getPastPlan(@ModelAttribute @Valid TeamIdRequest planRequest) {
        List<PastPlan> response = planService.getPastPlan(planRequest);
        return new BaseResponse<>(response);
    }

    *//**
     * 확정 약속 - 모임 내 사이드바 메뉴
     * *//*
    @GetMapping("/fixed")
    public BaseResponse<List<SideMenuFixedPlan>> getFixedPlan(@ModelAttribute @Valid TeamIdRequest planRequest) {
        List<SideMenuFixedPlan> response = planService.getFixedPlan(planRequest);
        return new BaseResponse<>(response);
    }

    @GetMapping("/member-plan")
    public BaseResponse<List<MemberIsInPlanResponse>> getMemberIsInPlan(@ModelAttribute @Valid PlanIdRequest planRequest) {
        List<MemberIsInPlanResponse> responses = planService.getMemberIsInPlan(planRequest);
        return new BaseResponse<>(responses);
    }
*/
}