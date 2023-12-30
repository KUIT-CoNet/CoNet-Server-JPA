package com.kuit.conet.jpa.repository;

import com.kuit.conet.domain.plan.TeamFixedPlanOnDay;
import com.kuit.conet.jpa.domain.plan.Plan;
import com.kuit.conet.jpa.domain.plan.PlanStatus;
import com.kuit.conet.jpa.domain.team.Team;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
public class PlanRepository {
    private final EntityManager em;

    public Long save(Plan plan) {
        em.persist(plan);
        return plan.getId();
    }

    public List<TeamFixedPlanOnDay> getFixedPlansOnDay(Long teamId, String searchDate) {
        return em.createQuery("select new com.kuit.conet.domain.plan.TeamFixedPlanOnDay(p.id, p.name, p.fixedTime) " +
                "from Plan p join p.team t on t.id=:teamId " +
                "where p.status=:status " +
                "and FUNCTION('DATE_FORMAT', p.fixedDate, '%Y-%m-%d')=:searchDate " +
                "order by p.fixedTime", TeamFixedPlanOnDay.class)
                .setParameter("teamId", teamId)
                .setParameter("status", PlanStatus.FIXED)
                .setParameter("searchDate", searchDate)
                .getResultList();
    }

    public List<Date> getFixedPlansInMonth(Long teamId, String searchMonth) {
        return em.createQuery("select distinct p.fixedDate " +
                        "from Plan p join p.team t on t.id=:teamId " +
                        "where p.status=:status " +
                        "and FUNCTION('DATE_FORMAT', p.fixedDate, '%Y-%m')=:searchMonth " +
                        "order by p.fixedDate", Date.class)
                .setParameter("teamId", teamId)
                .setParameter("status", PlanStatus.FIXED)
                .setParameter("searchMonth", searchMonth)
                .getResultList();
    }
}
