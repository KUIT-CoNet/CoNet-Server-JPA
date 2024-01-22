package com.kuit.conet.jpa.repository;

import com.kuit.conet.jpa.domain.team.TeamMember;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Getter
public class TeamMemberRepository {
    private final EntityManager em;

    public Long save(TeamMember teamMember) {
        em.persist(teamMember);
        return teamMember.getId();
    }

    public Boolean isBookmark(Long userId, Long teamId) {
        return em.createQuery("select tm.bookMark from TeamMember tm where tm.team.id=:teamId and tm.member.id=:userId", boolean.class)
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getSingleResult();
    }

    public TeamMember findByTeamIdAndUserId(Long teamId, Long userId) {
        return em.createQuery("select tm from TeamMember tm where tm.team.id=:teamId and tm.member.id=:userId", TeamMember.class)
                .setParameter("teamId", teamId)
                .setParameter("userId", userId)
                .getSingleResult();
    }


    public Long getCount(Long teamId) {
        return em.createQuery("select count(tm) from TeamMember tm where tm.team.id=:teamId", Long.class)
                .setParameter("teamId", teamId)
                .getSingleResult();
    }

    public boolean isTeamMember(Long teamId, Long userId) {
        return em.createQuery("select count(tm)>0 from TeamMember tm where tm.team.id=:teamId and tm.member.id=:userId", Boolean.class)
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getSingleResult();
    }

    public void deleteTeamMemberByTeamId(Long teamId) {
        em.createQuery("delete from TeamMember tm where tm.team.id=:teamId")
                .setParameter("teamId", teamId)
                .executeUpdate();
    }
}
