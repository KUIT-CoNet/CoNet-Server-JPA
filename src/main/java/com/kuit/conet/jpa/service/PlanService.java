package com.kuit.conet.jpa.service;


import com.kuit.conet.dto.plan.SideMenuFixedPlan;
import com.kuit.conet.dto.plan.WaitingPlan;
import com.kuit.conet.dto.plan.FixedPlanOnDay;
import com.kuit.conet.dto.web.request.plan.CreatePlanRequest;
import com.kuit.conet.dto.web.request.plan.TeamFixedPlanOnDateRequest;
import com.kuit.conet.dto.web.request.plan.TeamWaitingPlanRequest;
import com.kuit.conet.dto.web.response.plan.*;
import com.kuit.conet.jpa.domain.plan.Plan;
import com.kuit.conet.jpa.domain.team.Team;
import com.kuit.conet.jpa.repository.PlanRepository;
import com.kuit.conet.jpa.repository.TeamRepository;
import com.kuit.conet.utils.DateFormatter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private final TeamRepository teamRepository;
    private final PlanRepository planRepository;

    public CreatePlanResponse createPlan(CreatePlanRequest planRequest) {
        Date endDate = setEndDate(planRequest.getPlanStartDate());
        Team team = teamRepository.findById(planRequest.getTeamId());

        Plan newPlan = Plan.createPlan(team, planRequest.getPlanName(), planRequest.getPlanStartDate(), endDate);
        Long planId = planRepository.save(newPlan);

        return new CreatePlanResponse(planId);
    }

    private static Date setEndDate(Date startDate) {
        LocalDate endDate = startDate.toLocalDate().plusDays(6);
        return Date.valueOf(endDate);
    }

    public TeamPlanOnDayResponse getFixedPlanOnDay(TeamFixedPlanOnDateRequest planRequest) {
        List<FixedPlanOnDay> fixedPlansOnDay = planRepository.getFixedPlansOnDay(planRequest.getTeamId(), planRequest.getSearchDate());

        return new TeamPlanOnDayResponse(fixedPlansOnDay.size(), fixedPlansOnDay);
    }

    public PlanDateOnMonthResponse getFixedPlanInMonth(TeamFixedPlanOnDateRequest planRequest) {
        List<Date> fixedPlansInMonth = planRepository.getFixedPlansInMonth(planRequest.getTeamId(), planRequest.getSearchDate());
        List<Integer> planDates = DateFormatter.datesToIntegerList(fixedPlansInMonth);

        return new PlanDateOnMonthResponse(planDates.size(), planDates);
    }

    public WaitingPlanResponse getTeamWaitingPlan(TeamWaitingPlanRequest planRequest) {
        List<WaitingPlan> teamWaitingPlans = planRepository.getTeamWaitingPlan(planRequest.getTeamId());

        return new WaitingPlanResponse(teamWaitingPlans.size(), teamWaitingPlans);
    }

    public SideMenuFixedPlanResponse getFixedPastPlan(HttpServletRequest httpRequest, Long teamId) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        List<SideMenuFixedPlan> fixedPastPlans = planRepository.getFixedPastPlans(teamId, userId);
        return new SideMenuFixedPlanResponse(fixedPastPlans.size(), fixedPastPlans);
    }

    public SideMenuFixedPlanResponse getFixedFuturePlan(HttpServletRequest httpRequest, Long teamId) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        List<SideMenuFixedPlan> fixedPastPlans = planRepository.getFixedFuturePlans(teamId, userId);
        return new SideMenuFixedPlanResponse(fixedPastPlans.size(), fixedPastPlans);
    }

}