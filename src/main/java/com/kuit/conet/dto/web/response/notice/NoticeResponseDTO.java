package com.kuit.conet.dto.web.response.notice;

import com.kuit.conet.jpa.domain.notice.Notice;
import com.kuit.conet.utils.DateAndTimeFormatter;
import lombok.Getter;

@Getter
public class NoticeResponseDTO {
    private String title;
    private String content;
    private String date;

    public NoticeResponseDTO(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.date = DateAndTimeFormatter.dateToStringWithDot(notice.getDate());
    }
}
