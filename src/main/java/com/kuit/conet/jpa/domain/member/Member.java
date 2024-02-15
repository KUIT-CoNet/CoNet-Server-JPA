package com.kuit.conet.jpa.domain.member;

import com.kuit.conet.jpa.domain.auth.Platform;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String platform;

    @Column(length = 500)
    private String platformId;

    @Column(length = 500)
    private String imgUrl;

    private Boolean serviceTerm; // 필수 약관
    private Boolean optionTerm;

    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member createMember(String email, Platform platform, String platformId) {
        Member member = new Member();

        member.email = email;
        member.platform = platform.getPlatform();
        member.platformId = platformId;

        return member;
    }

    public void updateImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
