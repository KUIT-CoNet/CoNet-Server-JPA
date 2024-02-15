package com.kuit.conet.dto.web.response.member;

import com.kuit.conet.jpa.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDTO {
    private String name;
    private String email;
    private String userImgUrl;
    private String platform;

    public MemberResponseDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.userImgUrl = member.getImgUrl();
        this.platform = member.getPlatform();
    }
}