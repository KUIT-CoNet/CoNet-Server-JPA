package com.kuit.conet.jpa.repository;

import com.kuit.conet.jpa.domain.plan.Plan;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlanMemberTimeRepository {
    private final EntityManager em;

    public int deleteOnPlan(Plan plan) {
        int deletedPlanCount = em.createQuery("delete from PlanMemberTime pmt where pmt.plan=:plan")
                .setParameter("plan", plan)
                .executeUpdate();
        em.flush();
        return deletedPlanCount;
    }
}
