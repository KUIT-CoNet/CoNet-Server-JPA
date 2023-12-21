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
}
