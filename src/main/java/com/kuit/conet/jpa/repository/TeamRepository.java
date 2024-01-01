package com.kuit.conet.jpa.repository;

import com.kuit.conet.jpa.domain.team.Team;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        return em.createQuery("select count(t.id) > 0 from Team t where t.inviteCode=:inviteCode",Boolean.class)
                .setParameter("inviteCode",inviteCode)
                .getSingleResult();
    }

    public boolean isExistUser(Long id, Long userId) {
        return em.createQuery("select count(tm.id) > 0 from Team t join t.teamMembers tm on tm.member.id=:userId",Boolean.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }
}
