package com.kuit.conet.jpa.repository;

import com.kuit.conet.dto.plan.MemberAvailableTimeDTO;
import com.kuit.conet.jpa.domain.plan.Plan;
import com.kuit.conet.jpa.domain.plan.PlanMemberTime;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

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

    public void deleteByTeamId(Long teamId) {
        em.createQuery("delete from PlanMemberTime pmt where pmt.plan.id in " +
                "(select p.id from Plan p where p.team.id=:teamId)")
                .setParameter("teamId",teamId)
                .executeUpdate();
    }

    public boolean isUserAvailableTimeDataExist(Plan plan, Long userId) {
        return em.createQuery("select count(pmt) > 0 from PlanMemberTime pmt where pmt.plan=:plan and pmt.member.id=:userId", Boolean.class)
                .setParameter("plan", plan)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public List<PlanMemberTime> findByTeamAndMemberId(Plan plan, Long userId) {
        return em.createQuery("select pmt " +
                "from PlanMemberTime pmt where pmt.plan=:plan and pmt.member.id=:userId " +
                "order by pmt.date", PlanMemberTime.class)
                .setParameter("plan", plan)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<MemberAvailableTimeDTO> findMemberAndAvailableTimeOnDay(Plan plan, Date searchDate) {
        return em.createQuery("select new com.kuit.conet.dto.plan.MemberAvailableTimeDTO(pmt.member.id, pmt.member.name, pmt.availableTime) " +
                        "from PlanMemberTime pmt join pmt.member m " +
                        "where pmt.plan=:plan and pmt.date=:searchDate", MemberAvailableTimeDTO.class)
                .setParameter("plan", plan)
                .setParameter("searchDate", searchDate)
                .getResultList();

    }
}
