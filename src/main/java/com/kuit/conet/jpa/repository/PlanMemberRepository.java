package com.kuit.conet.jpa.repository;

import com.kuit.conet.jpa.domain.plan.Plan;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlanMemberRepository {
    private final EntityManager em;
    public int deleteOnPlan(Plan plan) {
        int deletedPlanCount = em.createQuery("delete from PlanMember pm where pm.plan=:plan")
                .setParameter("plan", plan)
                .executeUpdate();
        em.flush();
        return deletedPlanCount;
    }

    public void deleteByTeamId(Long teamId) {
        em.createQuery("delete from PlanMember pm where pm.plan.id in " +
                        "(select p.id from Plan p where p.team.id=:teamId)")
                .setParameter("teamId",teamId)
                .executeUpdate();
    }
}