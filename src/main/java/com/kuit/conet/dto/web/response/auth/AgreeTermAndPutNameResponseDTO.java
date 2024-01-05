package com.kuit.conet.dto.web.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgreeTermAndPutNameResponseDTO {
    private String name;
    private String email;
    private Boolean serviceTerm;
    private Boolean optionTerm;
}
