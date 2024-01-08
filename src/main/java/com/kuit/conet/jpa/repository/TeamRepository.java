package com.kuit.conet.jpa.repository;

import com.kuit.conet.jpa.domain.team.Team;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
public class TeamRepository {
    private final EntityManager em;

    public Long save(Team team) {
        em.persist(team);
        return team.getId();
    }

    public void remove(Team team) {
        em.remove(team);
    }

    public Team findById(Long id) {
        return em.find(Team.class, id);
    }

    public Team findByInviteCode(String inviteCode) {
        return em.createQuery("select t from Team t where t.inviteCode=:inviteCode", Team.class)
                .setParameter("inviteCode", inviteCode)
                .getSingleResult();
    }

    public boolean isExistInviteCode(String inviteCode) {
        return em.createQuery("select count(t.id) > 0 from Team t where t.inviteCode=:inviteCode", Boolean.class)
                .setParameter("inviteCode", inviteCode)
                .getSingleResult();
    }

    public boolean isExistUser(Long teamId, Long userId) {
        return em.createQuery("select count(tm.id) > 0 from Team t join t.teamMembers tm on t.id =:teamId and  tm.member.id=:userId", Boolean.class)
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getSingleResult();
    }

    public List<Team> findByUserId(Long userId) {
        return em.createQuery("select t from Team t join t.teamMembers tm on tm.member.id=:userId", Team.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Long getMemberCount(Long id) {
        //todo 컬렉션 연관 필드 조인 관련 수정
        return em.createQuery("select count(tm) from Team t join t.teamMembers tm on tm.team.id=:teamId", Long.class)
                .setParameter("teamId", id)
                .getSingleResult();
    }

    public boolean isTeamMember(Team team, Long userId) {
        return em.createQuery("select count(tm)>0 from TeamMember tm join tm.member on tm.member.id=:userId", Boolean.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public String getTeamImgUrl(Long teamId) {
        return em.createQuery("select t.imgUrl from Team t where t.id=:teamId", String.class)
                .setParameter("teamId", teamId)
                .getSingleResult();
    }

    public void deleteTeam(Long teamId) {
        em.createQuery("delete from Team t where t.id=:teamId")
                .setParameter("teamId", teamId)
                .executeUpdate();
    }
}
