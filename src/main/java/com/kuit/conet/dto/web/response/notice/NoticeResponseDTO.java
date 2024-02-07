package com.kuit.conet.dto.web.response.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeResponseDTO {
    private String title;
    private String content;
    private String date;

}
