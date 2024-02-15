package com.kuit.conet.dto.web.response.member;

import com.kuit.conet.domain.auth.Platform;
import com.kuit.conet.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDTO {
    private String name;
    private String email;
    private String userImgUrl;
    private Platform platform;

    public MemberResponseDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.userImgUrl = member.getImgUrl();
        this.platform = member.getPlatform();
    }
}