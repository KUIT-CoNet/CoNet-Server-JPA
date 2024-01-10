package com.kuit.conet.jpa.service;


import com.kuit.conet.dto.plan.*;
import com.kuit.conet.dto.web.request.plan.*;
import com.kuit.conet.dto.web.response.plan.*;
import com.kuit.conet.jpa.domain.plan.*;
import com.kuit.conet.jpa.domain.team.Team;
import com.kuit.conet.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.kuit.conet.jpa.service.validator.PlanValidator.*;
import static com.kuit.conet.utils.DateAndTimeFormatter.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final PlanRepository planRepository;
    private final PlanMemberTimeRepository planMemberTimeRepository;

    public CreatePlanResponseDTO createPlan(CreatePlanRequestDTO planRequest) {
        Date endDate = setEndDate(planRequest.getPlanStartDate());
        Team team = teamRepository.findById(planRequest.getTeamId());

        Plan newPlan = Plan.createPlan(team, planRequest.getPlanName(), planRequest.getPlanStartDate(), endDate);
        Long planId = planRepository.save(newPlan);

        return new CreatePlanResponseDTO(planId);
    }

    private static Date setEndDate(Date startDate) {
        LocalDate endDate = startDate.toLocalDate().plusDays(6);
        return Date.valueOf(endDate);
    }

    public PlanDetailResponseDTO getPlanDetail(Long planId) {
        Plan plan = planRepository.findWithMembersById(planId);

        List<PlanMemberDTO> planMemberList = setPlanMemberDTOS(plan);

        return new PlanDetailResponseDTO(plan, planMemberList);
    }

    public TeamPlanOnDayResponseDTO getFixedPlanOnDay(TeamFixedPlanOnDateRequestDTO planRequest) {
        List<FixedPlanOnDayDTO> fixedPlansOnDay = planRepository.getFixedPlansOnDay(planRequest.getTeamId(), planRequest.getSearchDate());

        return new TeamPlanOnDayResponseDTO(fixedPlansOnDay);
    }

    public PlanDateOnMonthResponseDTO getFixedPlanInMonth(TeamFixedPlanOnDateRequestDTO planRequest) {
        List<Date> fixedPlansInMonth = planRepository.getFixedPlansInMonth(planRequest.getTeamId(), planRequest.getSearchDate());
        List<Integer> planDates = datesToIntegerList(fixedPlansInMonth);

        return new PlanDateOnMonthResponseDTO(planDates);
    }

    public WaitingPlanResponseDTO getTeamWaitingPlan(TeamWaitingPlanRequestDTO planRequest) {
        List<WaitingPlanDTO> teamWaitingPlans = planRepository.getTeamWaitingPlan(planRequest.getTeamId());

        return new WaitingPlanResponseDTO(teamWaitingPlans);
    }

    public SideMenuFixedPlanResponseDTO getFixedPastPlan(Long userId, Long teamId) {
        List<SideMenuFixedPlanDTO> fixedPastPlans = planRepository.getFixedPastPlans(teamId, userId);
        return new SideMenuFixedPlanResponseDTO(fixedPastPlans);
    }

    public SideMenuFixedPlanResponseDTO getFixedOncomingPlan(Long userId, Long teamId) {
        List<SideMenuFixedPlanDTO> fixedOncomingPlans = planRepository.getFixedOncomingPlans(teamId, userId);
        return new SideMenuFixedPlanResponseDTO(fixedOncomingPlans);
    }

    public FixPlanResponseDTO fixPlan(FixPlanRequestDTO planRequest) {
        Plan plan = planRepository.findById(planRequest.getPlanId());

        //이미 확정된 약속인지 검사
        validatePlanIsAlreadyFixed(plan);
        //범위 내에 존재하는 날짜인지 검사
        validateDateInPeriod(planRequest.getFixedDate(), plan);

        Time fixedTime = timeIntegerToTime(planRequest);

        //Plan에 확정 날짜와 시간, status update - 변경 감지 이용
        plan.fixPlan(planRequest.getFixedDate(), fixedTime);

        //확정한 약속의 구성원
        setPlanMember(planRequest.getUserIds(), plan);

        //해당 약속의 모든 PlanMemberTime 삭제
        deletePlanMemberTime(plan);

        return new FixPlanResponseDTO(plan);
    }

    private void setPlanMember(List<Long> userIds, Plan plan) {
        //cascade로 영속화
        userIds.forEach(userId
                -> PlanMember.createPlanMember(plan, memberRepository.findById(userId)));
    }

    private void deletePlanMemberTime(Plan plan) {
        int deletedPlanMemberTimeCount = planMemberTimeRepository.deleteOnPlan(plan);
        log.info("약속을 확정하며 삭제된 PlanMemberTime 개수: {}", deletedPlanMemberTimeCount);
    }

    private static List<PlanMemberDTO> setPlanMemberDTOS(Plan plan) {
        return plan.getPlanMembers().stream()
                .map(planMember ->
                        new PlanMemberDTO(
                                planMember.getMember().getId(),
                                planMember.getMember().getName(),
                                planMember.getMember().getImgUrl()))
                .sorted(Comparator.comparing(PlanMemberDTO::getName))
                .toList();
    }

    public UserAvailableTimeResponseDTO getUserAvailableTimeSlot(Long planId, Long userId) {
        Plan plan = planRepository.findById(planId);

        // 대기 중인 약속일 때만 나의 가능한 시간 조회
        validatePlanIsWaiting(plan);

        // 유저의 시간 정보 유무 조회
        if(!planMemberTimeRepository.isUserAvailableTimeDataExist(plan, userId)) {
            return UserAvailableTimeResponseDTO.notRegistered(planId, userId);
        }

        // 가능한 시간 정보 조회
        List<PlanMemberTime> planMemberTimes = planMemberTimeRepository.findByTeamAndMemberId(plan, userId);
        List<UserAvailableTimeDTO> timeSlot = getAvailableTimeSlot(planMemberTimes);

        // 가능한 시간이 존재하는지
        Boolean hasPossibleTime = timeSlot.stream()
                .anyMatch(timeSlotOnDay -> !timeSlotOnDay.getAvailableTimes().isEmpty());

        return UserAvailableTimeResponseDTO.registered(planId, userId, hasPossibleTime, timeSlot);
    }

    private static List<UserAvailableTimeDTO> getAvailableTimeSlot(List<PlanMemberTime> planMemberTimes) {
         return planMemberTimes.stream()
                .map(planMemberTime -> {
                    UserAvailableTimeDTO responseDTO = new UserAvailableTimeDTO(planMemberTime.getDate());
                    String possibleTime = planMemberTime.getPossibleTime();

                    List<Integer> timeList = new ArrayList<>();
                    if (!possibleTime.isEmpty()) {
                        timeList = timeStringToIntegerList(possibleTime);
                    }
                    responseDTO.setAvailableTimes(timeList);

                    return responseDTO;
                }).toList();
    }

}