package com.kuit.conet.jpa.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
//TODO: 생성 메서드 사용하게 되면 @NoArgsConstructor(access = AccessLevel.PROTECTED) 설정 -> 생성메서드 외 생성자 사용 방지
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    //TODO: 약관 boolean, status enum 변경, 노션 트러블슈팅 보고 default 값 설정
    private Integer serviceTerm; // 필수 약관
    private Integer optionTerm;
    private Integer status;
  
}
