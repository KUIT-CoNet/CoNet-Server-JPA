package com.kuit.conet.controller;

import com.kuit.conet.annotation.UserId;
import com.kuit.conet.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    @GetMapping
    public BaseResponse<NoticeResponseDTO> getNotice(@UserId Long userId) {
        NoticeResponseDTO response = noticeService.getNotice(userId);
        return new BaseResponse<NoticeResponseDTO>(response);
    }
}
