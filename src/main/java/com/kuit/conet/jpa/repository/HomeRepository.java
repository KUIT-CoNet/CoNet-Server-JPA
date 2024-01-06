package com.kuit.conet.jpa.repository;

import com.kuit.conet.dto.home.HomeFixedPlanOnDayDTO;
import com.kuit.conet.dto.plan.WaitingPlanDTO;
import com.kuit.conet.jpa.domain.plan.PlanStatus;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
public class HomeRepository {
    private final EntityManager em;

    public List<Date> getHomeFixedPlansInMonth(Long userId, String searchDate) {
        // 해당 년, 월에 유저가 포함된 모든 모임의 모든 약속 -> fixed_date 만 distinct 로 검색
        // team_member(userId, status) -> plan(teamId, fixed_date, status)

        return em.createQuery("select distinct p.fixedDate " +
                        "from TeamMember tm join Plan p on tm.team.id = p.team.id " +
                        "where tm.member.id = :userId " +
                        "and p.status = :status " +
                        "and FUNCTION('DATE_FORMAT', p.fixedDate, '%Y-%m') = :searchDate", Date.class)
                .setParameter("userId", userId)
                .setParameter("status", PlanStatus.FIXED)
                .setParameter("searchDate", searchDate)
                .getResultList();
    }

    public List<HomeFixedPlanOnDayDTO> getHomeFixedPlansOnDay(Long userId, String searchDate) {
        return em.createQuery("select new com.kuit.conet.dto.home.HomeFixedPlanOnDayDTO(p.id, p.fixedTime, p.team.name, p.name) " +
                        "from TeamMember tm join Plan p on tm.team.id = p.team.id " +
                        "where tm.member.id = :userId " +
                        "and p.status=:status " +
                        "and FUNCTION('DATE_FORMAT', p.fixedDate, '%Y-%m-%d')=:searchDate " +
                        "order by p.fixedTime", HomeFixedPlanOnDayDTO.class)
                .setParameter("userId", userId)
                .setParameter("status", PlanStatus.FIXED)
                .setParameter("searchDate", searchDate)
                .getResultList();
    }

    public List<WaitingPlanDTO> getHomeWaitingPlans(Long userId) {
        // 유저가 속한 모든 모임의 tm.userId=:user_id & tm.team_id = p.team_id
        // 모든 대기 중인 약속 중에서 p.status=WAITING
        // 시작 날짜가 오늘 이후 plan_start_period >= current_date();

        return em.createQuery("select new com.kuit.conet.dto.plan.WaitingPlanDTO(p.id, p.startPeriod, p.endPeriod, p.team.name, p.name) " +
                        "from TeamMember tm join Plan p on tm.team.id=p.team.id " +
                        "where tm.member.id=:userId " +
                        "and p.status=:status " +
                        "and p.startPeriod>=current_date() " +
                        "order by p.startPeriod", WaitingPlanDTO.class)
                .setParameter("userId", userId)
                .setParameter("status", PlanStatus.WAITING)
                .getResultList();
    }
}
