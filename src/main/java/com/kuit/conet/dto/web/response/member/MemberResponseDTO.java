package com.kuit.conet.dto.web.response.member;

import com.kuit.conet.jpa.domain.auth.Platform;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberResponseDTO {
    private String name;
    private String email;
    private String userImgUrl;
    private Platform platform;
}