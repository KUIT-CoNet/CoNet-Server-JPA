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

    public String updateFixedPlan(UpdatePlanRequest planRequest, MultipartFile file) {
        //TODO: history 내용 삭제
        Long planId = planRequest.getPlanId();

        if (!planDao.isFixedPlan(planId)) {
            throw new PlanException(NOT_FIXED_PLAN);
        }

        // plan 테이블 정보 수정
        planDao.updateFixedPlan(planRequest);

        // 이미 history 에 등록된 약속이면 history 수정
        if (planRequest.getIsRegisteredToHistory()) {
            // 기존 이미지 삭제 작업 진행 - 존재하지 않으면 생략
            if (historyDao.isHistoryImageExist(planId)) {
                String imgUrl = historyDao.getHistoryImgUrl(planId);
                String deleteFileName = storageService.getFileNameFromUrl(imgUrl);
                storageService.deleteImage(deleteFileName);
            }

            // 수정된 값의 이미지와 상세 내용 존재 여부를 판단
            // -> 둘 다 null 인 경우 히스토리 데이터 삭제 및 plan 테이블에 history=0 으로 수정
            if (planRequest.getHistoryDescription()==null && file.isEmpty()) {
                log.info("히스토리 정보가 비어있습니다. 해당 약속의 히스토리 데이터를 삭제합니다.");

                // 기존 히스토리 정보에 이미지 존재시 S3 객체 삭제
                planDao.setHistoryInactive(planId);
                historyDao.deleteHistory(planId);
            } else {
                String imgUrl = null;
                if (!file.isEmpty()) {
                    // 저장할 파일명 만들기 - 받은 파일이 이미지 타입이 아닌 경우에 대한 유효성 검사 진행
                    String fileName = storageService.getFileName(file, StorageDomain.HISTORY, planId);
                    // 새로운 이미지 S3에 업로드
                    imgUrl = storageService.uploadToS3(file, fileName);
                }

                History newHistory = new History(imgUrl, planRequest.getHistoryDescription());

                historyDao.updateHistory(newHistory, planId);
            }
        }

        return "약속 정보를 수정하였습니다.";
    }

    public List<MemberIsInPlanResponse> getMemberIsInPlan(PlanIdRequest planIdRequest) {
        Long planId = planIdRequest.getPlanId();

        return planDao.getMemberIsInPlanId(planId);
    }
}*/
