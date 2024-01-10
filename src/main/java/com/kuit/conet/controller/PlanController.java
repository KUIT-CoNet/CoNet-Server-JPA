package com.kuit.conet.controller;

import com.kuit.conet.annotation.UserId;
import com.kuit.conet.common.exception.PlanException;
import com.kuit.conet.common.response.BaseResponse;
import com.kuit.conet.dto.web.response.plan.PlanDetailResponseDTO;
import com.kuit.conet.dto.web.request.plan.TeamFixedPlanInPeriodRequestDTO;
import com.kuit.conet.dto.web.request.plan.*;
import com.kuit.conet.dto.web.response.plan.*;
import com.kuit.conet.jpa.domain.plan.PlanPeriod;
import com.kuit.conet.jpa.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST;

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
    public BaseResponse<CreatePlanResponseDTO> createPlan(@RequestBody @Valid CreatePlanRequestDTO planRequest) {
        CreatePlanResponseDTO response = planService.createPlan(planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 약속 상세 정보 조회 api
     */
    @GetMapping("/{planId}")
    public BaseResponse<PlanDetailResponseDTO> getPlan(@PathVariable @Valid Long planId) {
        PlanDetailResponseDTO response = planService.getPlanDetail(planId);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 모임 내 특정 날짜 확정 약속 조회 api
     * / '나'의 직접적인 참여 여부와 무관
     * */
    @GetMapping("/day")
    public BaseResponse<TeamPlanOnDayResponseDTO> getFixedPlanOnDay(@ModelAttribute @Valid TeamFixedPlanOnDateRequestDTO planRequest) {
        TeamPlanOnDayResponseDTO response = planService.getFixedPlanOnDay(planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 모임 내 특정 달의 약속이 존재하는 날짜 조회 api
     */
    @GetMapping("/month")
    public BaseResponse<PlanDateOnMonthResponseDTO> getFixedPlanInMonth(@ModelAttribute @Valid TeamFixedPlanOnDateRequestDTO planRequest) {
        PlanDateOnMonthResponseDTO response = planService.getFixedPlanInMonth(planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote [사이드바 > 대기중인 약속] 모임 내 대기 중인 약속 조회 api
     * / '나'의 직접적인 참여 여부와 무관
     */
    @GetMapping("/waiting")
    public BaseResponse<WaitingPlanResponseDTO> getWaitingPlan(@ModelAttribute @Valid TeamWaitingPlanRequestDTO planRequest) {
        WaitingPlanResponseDTO response = planService.getTeamWaitingPlan(planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote [사이드바 > 내 약속] 모임 내 확정된 (지난/다가오는) 약속 조회 api
     * / 조회한 유저의 참여 여부 포함
     */
    @GetMapping("/fixed")
    public BaseResponse<SideMenuFixedPlanResponseDTO> getFixedPlan(@UserId @Valid Long userId, @ModelAttribute TeamFixedPlanInPeriodRequestDTO planRequest) {
        // 지난 약속
        if (planRequest.getPeriod() == PlanPeriod.PAST) {
            SideMenuFixedPlanResponseDTO response = planService.getFixedPastPlan(userId, planRequest.getTeamId());
            return new BaseResponse<>(response);
        }

        // 다가오는 약속
        if (planRequest.getPeriod() == PlanPeriod.ONCOMING) {
            SideMenuFixedPlanResponseDTO response = planService.getFixedOncomingPlan(userId, planRequest.getTeamId());
            return new BaseResponse<>(response);
        }

        throw new PlanException(BAD_REQUEST);
    }

    /**
     * @apiNote 약속 확정 api
     */
    @PostMapping("/fix")
    public BaseResponse<FixPlanResponseDTO> fixPlan(@RequestBody @Valid FixPlanRequestDTO planRequest) {
        FixPlanResponseDTO response = planService.fixPlan(planRequest);
        return new BaseResponse<>(response);
    }

    /**
     * @apiNote 특정 약속의 나의 가능한 시간 조회 api
     */
    @GetMapping("/{planId}/available-time-slot/my")
    public BaseResponse<UserAvailableTimeResponseDTO> getUserTimeSlot(@PathVariable @Valid Long planId, @UserId @Valid Long userId) {
        UserAvailableTimeResponseDTO response = planService.getUserAvailableTimeSlot(planId, userId);
        return new BaseResponse<>(response);
    }

/*
    @PostMapping("/time")
    public BaseResponse<String> registerTime(HttpServletRequest httpRequest, @RequestBody @Valid PossibleTimeRequest request) {
        planService.saveTime(request, httpRequest);
        return new BaseResponse<>("사용자의 가능한 시간 등록에 성공하였습니다.");
    }

    @GetMapping("/member-time")
    public BaseResponse<MemberPossibleTimeResponse> getMemberTime(@ModelAttribute @Valid PlanIdRequest request) {
        MemberPossibleTimeResponse response = planService.getMemberTime(request);
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

    @GetMapping("/member-plan")
    public BaseResponse<List<MemberIsInPlanResponse>> getMemberIsInPlan(@ModelAttribute @Valid PlanIdRequest planRequest) {
        List<MemberIsInPlanResponse> responses = planService.getMemberIsInPlan(planRequest);
        return new BaseResponse<>(responses);
    }
*/
}