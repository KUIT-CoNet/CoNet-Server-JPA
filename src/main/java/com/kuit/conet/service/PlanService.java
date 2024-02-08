/*
package com.kuit.conet.service;

import com.kuit.conet.common.exception.PlanException;
import com.kuit.conet.dao.PlanDao;
import com.kuit.conet.dao.TeamDao;
import com.kuit.conet.dao.UserDao;
import com.kuit.conet.domain.plan.*;
import com.kuit.conet.jpa.domain.storage.StorageDomain;
import com.kuit.conet.dto.request.plan.*;
import com.kuit.conet.dto.request.team.TeamIdRequest;
import com.kuit.conet.dto.response.plan.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
//@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanDao planDao;
    private final UserDao userDao;
    private final TeamDao teamDao;
    private final StorageService storageService;

    public String deletePlan(PlanIdRequest planRequest) {
        //TODO: history 내용 삭제
        Long planId = planRequest.getPlanId();
        Boolean isFixedPlan;

        if (planDao.isFixedPlan(planId)) isFixedPlan = true;
        else isFixedPlan = false;

        if (planDao.isRegisteredToHistory(planId)) { // 히스토리 등록된 약속
            // 이미지 객체 삭제
            String imgUrl = historyDao.getHistoryImgUrl(planId);
            String deleteFileName = storageService.getFileNameFromUrl(imgUrl);
            storageService.deleteImage(deleteFileName);

            historyDao.deleteHistory(planId);
        }

        planDao.deletePlan(planId, isFixedPlan);
        return "약속 삭제에 성공하였습니다.";
    }

    public String updateWaitingPlan(UpdateWaitingPlanRequest planRequest) {
        if (!planDao.isWaitingPlan(planRequest.getPlanId())) {
            throw new PlanException(NOT_WAITING_PLAN);
        }

        planDao.updateWaitingPlan(planRequest.getPlanId(), planRequest.getPlanName());
        return "약속 수정을 성공하였습니다.";
    }

}*/
