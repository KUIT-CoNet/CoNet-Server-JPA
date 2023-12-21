package com.kuit.conet.jpa.repository;

import com.kuit.conet.jpa.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Getter
public class UserRepository {
    private final EntityManager em;

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }
}
