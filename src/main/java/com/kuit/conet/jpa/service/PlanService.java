package com.kuit.conet.jpa.service;


import com.kuit.conet.dto.plan.*;
import com.kuit.conet.dto.web.request.plan.*;
import com.kuit.conet.dto.plan.AvailableMemberDTO;
import com.kuit.conet.dto.plan.MemberAvailableTimeDTO;
import com.kuit.conet.dto.plan.MemberDateTimeDTO;
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
import java.util.*;

import static com.kuit.conet.jpa.service.validator.PlanValidator.*;
import static com.kuit.conet.utils.DateAndTimeFormatter.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PlanRepository planRepository;
    private final PlanMemberTimeRepository planMemberTimeRepository;

    private final static int ONE_WEEK_DAYS = 7;
    private final static int ONE_DAY_HOURS = 24;
    private final static int MIN_TIME_NUMBER = 0;
    private final static int MAX_TIME_NUMBER = 23;
    private final static String AVAILABLE_TIME_SPLIT_REGEX = ",";
    private final static int SECTION_DIVISOR = 3;
    private final static String SECTION = "section";
    private final static String INTERVAL_NUMBER = "intervalEndNumberForEachSection";

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
        Boolean hasAvailableTime = timeSlot.stream()
                .anyMatch(timeSlotOnDay -> !timeSlotOnDay.getAvailableTimes().isEmpty());

        return UserAvailableTimeResponseDTO.registered(planId, userId, hasAvailableTime, timeSlot);
    }

    private static List<UserAvailableTimeDTO> getAvailableTimeSlot(List<PlanMemberTime> planMemberTimes) {
         return planMemberTimes.stream()
                .map(planMemberTime -> {
                    UserAvailableTimeDTO responseDTO = new UserAvailableTimeDTO(planMemberTime.getDate());
                    String availableTime = planMemberTime.getAvailableTime();

                    List<Integer> timeList = new ArrayList<>();
                    if (!availableTime.isEmpty()) {
                        timeList = timeStringToIntegerList(availableTime);
                    }
                    responseDTO.setAvailableTimes(timeList);

                    return responseDTO;
                }).toList();
    }

    public MemberAvailableTimeResponseDTO getAvailableTimeSlot(Long planId) {
        Plan plan = planRepository.findById(planId);

        // 대기 중인 약속일 때만 구성원의 가능한 시간 조회
        validatePlanIsWaiting(plan);

        Date periodStartDate = plan.getStartPeriod();

        //모임 구성원 수 -> 구간 나눌 때 필요
        Long teamId = plan.getTeam().getId();
        Long teamMemberTotalCount = teamMemberRepository.getCount(teamId);

        //구간별 최대 인원수 구하기
        Map<Integer, Long> endNumberForEachSection = getIntervalEndBySection(teamMemberTotalCount);

        return new MemberAvailableTimeResponseDTO();
    }

    private static Map<Integer, Long> getIntervalEndBySection(Long totalMemberCount) {
        Map<Integer, Long> endNumberForEachSection = new HashMap<>();

        //기본 구간 크기와 나머지 계산
        Long baseIntervalSize = totalMemberCount / SECTION_DIVISOR;
        Long remainder = totalMemberCount % SECTION_DIVISOR;

        //3구간의 범위가 너무 크지 않게, 비교적 균등하게 범위가 나뉘도록 하기 위하여
        Long firstIntervalEnd = baseIntervalSize + (remainder > 0 ? 1 : 0);
        Long secondIntervalEnd = firstIntervalEnd + baseIntervalSize + (remainder > 1 ? 1 : 0);
        endNumberForEachSection.put(1, firstIntervalEnd);
        endNumberForEachSection.put(2, secondIntervalEnd);
        endNumberForEachSection.put(3, totalMemberCount);

        return endNumberForEachSection;
    }
}