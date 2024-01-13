package com.kuit.conet.jpa.repository;

import com.kuit.conet.dto.web.response.team.GetTeamResponseDTO;
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

    public Team findById(Long id) {
        return em.find(Team.class, id);
    }

    public Team findByInviteCode(String inviteCode) {
        return em.createQuery("select t from Team t where t.inviteCode=:inviteCode", Team.class)
                .setParameter("inviteCode", inviteCode)
                .getSingleResult();
    }

    public boolean isExistInviteCode(String inviteCode) {
        return em.createQuery("select count(t) > 0 from Team t where t.inviteCode=:inviteCode", Boolean.class)
                .setParameter("inviteCode", inviteCode)
                .getSingleResult();
    }

    public boolean isExistUser(Long teamId, Long userId) {
        return em.createQuery("select count(tm) > 0 from Team t join t.teamMembers tm on t.id =:teamId and tm.member.id=:userId", Boolean.class)
                .setParameter("userId", userId)
                .setParameter("teamId", teamId)
                .getSingleResult();
    }

    public List<Team> findByUserId(Long userId) {
        return em.createQuery("select t from Team t join t.teamMembers tm on tm.member.id=:userId", Team.class)
                .setParameter("userId", userId)
                .getResultList();
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

    public GetTeamResponseDTO getTeamDetail(Long teamId, Long userId) {
        return em.createQuery("select new com.kuit.conet.dto.web.response.team.GetTeamResponseDTO(t, size(t.teamMembers), tm.bookMark) " +
                        "from Team t join t.teamMembers tm where t.id = :teamId and tm.member.id=:userId", GetTeamResponseDTO.class)
                .setParameter("teamId", teamId)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
