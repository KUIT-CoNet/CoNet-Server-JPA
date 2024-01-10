package com.kuit.conet.jpa.repository;

import com.kuit.conet.dto.web.response.StorageImgResponseDTO;
import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.member.MemberStatus;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Getter
public class MemberRepository {
    private final EntityManager em;

    @Value("${spring.user.default-image}")
    private String defaultImg;

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public StorageImgResponseDTO getImgUrlResponse(Long userId) {
        return em.createQuery("select new com.kuit.conet.dto.web.response.StorageImgResponseDTO(m.name, m.imgUrl) " +
                        "from Member m where m.id=:userId and m.status=:status", StorageImgResponseDTO.class)
                .setParameter("userId", userId)
                .setParameter("status", MemberStatus.ACTIVE)
                .getSingleResult();
    }

    public Boolean isDefaultImage(Long userId) {
        return em.createQuery("select case when " +
                        "(select m.imgUrl from Member m where m.id=:userId and m.status=:status) " +
                        "= :defaultImg " +
                        "then TRUE else FALSE end from Member m", Boolean.class)
                .setParameter("userId", userId)
                .setParameter("status", MemberStatus.ACTIVE)
                .setParameter("defaultImg", defaultImg)
                .getSingleResult();
    }


}
