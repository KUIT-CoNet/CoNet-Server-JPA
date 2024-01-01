package com.kuit.conet.jpa.repository;

import com.kuit.conet.domain.plan.HomeFixedPlanOnDay;
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

    public List<HomeFixedPlanOnDay> getHomeFixedPlansOnDay(Long userId, String searchDate) {
        return em.createQuery("select new com.kuit.conet.domain.plan.HomeFixedPlanOnDay(p.id, p.fixedTime, p.team.name, p.name) " +
                        "from TeamMember tm join Plan p on tm.team.id = p.team.id " +
                        "where tm.member.id = :userId " +
                        "and p.status=:status " +
                        "and FUNCTION('DATE_FORMAT', p.fixedDate, '%Y-%m-%d')=:searchDate " +
                        "order by p.fixedTime", HomeFixedPlanOnDay.class)
                .setParameter("userId", userId)
                .setParameter("status", PlanStatus.FIXED)
                .setParameter("searchDate", searchDate)
                .getResultList();
    }
}
