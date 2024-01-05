package com.kuit.conet.dto.web.response.user;

import com.kuit.conet.jpa.domain.auth.Platform;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDTO {
    private String name;
    private String email;
    private String userImgUrl;
    private Platform platform;
}