/*
package com.kuit.conet.service;

import com.kuit.conet.common.exception.TeamException;
import com.kuit.conet.dao.TeamDao;
import com.kuit.conet.dao.UserDao;
import com.kuit.conet.domain.storage.StorageDomain;
import com.kuit.conet.domain.team.Team;
import com.kuit.conet.domain.team.TeamMember;
import com.kuit.conet.dto.request.team.CreateTeamRequest;
import com.kuit.conet.dto.request.team.ParticipateTeamRequest;
import com.kuit.conet.dto.request.team.TeamIdRequest;
import com.kuit.conet.dto.request.team.UpdateTeamRequest;
import com.kuit.conet.dto.response.StorageImgResponse;
import com.kuit.conet.dto.response.team.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
//@Service
@RequiredArgsConstructor
public class TeamService {
    private final StorageService storageService;
    private final TeamDao teamDao;
    private final UserDao userDao;

    public RegenerateCodeResponse regenerateCode(TeamIdRequest request) {
        String inviteCode;

        // 초대 코드 생성 및 중복 확인
        do {
            inviteCode = generateInviteCode();
        } while(teamDao.validateDuplicateCode(inviteCode));  // 중복되면 true 반환

        // 모임 생성 시간 찍기
        Timestamp codeGeneratedTime = Timestamp.valueOf(LocalDateTime.now());

        // 모임 존재 여부 확인
        if (!teamDao.isExistTeam(request.getTeamId())) {
            throw new TeamException(NOT_FOUND_TEAM);
        }

        // 초대 코드, 생성시간 update
        String newCode = teamDao.codeUpdate(request.getTeamId(), inviteCode, codeGeneratedTime);

        LocalDateTime codeDeadline = codeGeneratedTime.toLocalDateTime().plusDays(1);
        String codeDeadlineStr = codeDeadline.format(DateTimeFormatter.ofPattern("yyyy. MM. dd. HH:mm"));

        return new RegenerateCodeResponse(request.getTeamId(), newCode, codeDeadlineStr);
    }

    public String leaveTeam(TeamIdRequest teamIdRequest, HttpServletRequest httpRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));

        // 모임 존재 여부 확인
        if (!teamDao.isExistTeam(teamIdRequest.getTeamId())) {
            throw new TeamException(NOT_FOUND_TEAM);
        }

        teamDao.leaveTeam(teamIdRequest.getTeamId(), userId);

        return "모임 탈퇴에 성공하였습니다.";
    }

    public String deleteTeam(TeamIdRequest teamIdRequest) {
        // 모임 존재 여부 확인
        if (!teamDao.isExistTeam(teamIdRequest.getTeamId())) {
            throw new TeamException(NOT_FOUND_TEAM);
        }

        //TODO: history 내용 삭제
        List<String> ImgUrl = historyDao.getHistoryImgUrlFromTeamId(teamIdRequest.getTeamId());
        for(String url : historyImgUrl) {
            if(url != null) {
                String deleteFileName = storageService.getFileNameFromUrl(url);
                storageService.deleteImage(deleteFileName);
            }
        }

        String imgUrl = teamDao.getTeamImgUrl(teamIdRequest.getTeamId());

        if(!imgUrl.equals("")) {
            String deleteFileName = storageService.getFileNameFromUrl(imgUrl);
            storageService.deleteImage(deleteFileName);
        }

        teamDao.deleteTeam(teamIdRequest.getTeamId());

        return "모임 삭제에 성공하였습니다.";
    }

    public StorageImgResponse updateTeam(UpdateTeamRequest updateTeamRequest, MultipartFile file) {
        String fileName = storageService.getFileName(file, StorageDomain.TEAM, updateTeamRequest.getTeamId());

        if(!teamDao.isExistTeam(updateTeamRequest.getTeamId())) {
            return null;
        }

        String imgUrl = null;

        imgUrl = teamDao.getTeamImgUrl(updateTeamRequest.getTeamId());
        if(imgUrl != null) {
            String deleteFileName = storageService.getFileNameFromUrl(imgUrl);
            storageService.deleteImage(deleteFileName);
        }

        // 새로운 이미지 S3에 업로드
        imgUrl = storageService.uploadToS3(file, fileName);

        // image update
        StorageImgResponse response = teamDao.updateImg(updateTeamRequest.getTeamId(), imgUrl);

        // name update
        teamDao.updateName(updateTeamRequest.getTeamId(), updateTeamRequest.getTeamName());

        return response;
    }

    public List<GetTeamMemberResponse> getTeamMembers(TeamIdRequest teamIdRequest) {
        return teamDao.getTeamMembers(teamIdRequest.getTeamId());
    }

    public void bookmarkTeam(HttpServletRequest httpRequest, TeamIdRequest request) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        Long teamId = request.getTeamId();

        // 유저가 팀에 참가 중인지 검사
        if (!teamDao.isExistingUser(teamId, userId)) {
            throw new TeamException(USER_NOT_EXIST_IN_TEAM);
        }

        teamDao.bookmarkTeam(userId, teamId);
    }

    public void unBookmarkTeam(HttpServletRequest httpRequest, TeamIdRequest request) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        Long teamId = request.getTeamId();

        // 유저가 팀에 참가 중인지 검사
        if (!teamDao.isExistingUser(teamId, userId)) {
            throw new TeamException(USER_NOT_EXIST_IN_TEAM);
        }

        teamDao.unBookmarkTeam(userId, teamId);
    }

    public GetTeamResponse getTeamDetail(HttpServletRequest httpRequest, TeamIdRequest request) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        Long teamId = request.getTeamId();

        // 유저가 팀에 참가 중인지 검사
        if (!teamDao.isExistingUser(teamId, userId)) {
            throw new TeamException(USER_NOT_EXIST_IN_TEAM);
        }

        GetTeamResponse getTeamResponse = teamDao.getTeamDetail(teamId);
        getTeamResponse.setBookmark(teamDao.getBookmark(userId, teamId));
        return getTeamResponse;
    }

    public List<GetTeamResponse> getBookmarks(HttpServletRequest httpRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));

        List<Team> teamResponses = teamDao.getBookmarks(userId);
        List<GetTeamResponse> teamReturnResponses = new ArrayList<>();

        // 모임의 created_at 시간 비교해서 3일 안지났으면 isNew 값 true, 지났으면 false로 반환
        for(Team list : teamResponses) {
            log.info("{}", list.getTeamName());
            Timestamp createdTime = teamDao.getCreatedTime(list.getTeamId());
            // Timestamp를 Instant로 변환
            Instant instant = createdTime.toInstant();
            // Instant를 LocalDateTime으로 변환 (기본 시스템의 ZoneId 사용)
            LocalDateTime time = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

            LocalDateTime now = LocalDateTime.now();

            if(now.minusDays(3).isAfter(time)) {
                GetTeamResponse teamResponse = new GetTeamResponse(list.getTeamId(), list.getTeamName(), list.getTeamImgUrl(), teamDao.getTeamMemberCount(list.getTeamId()), false, teamDao.getBookmark(userId, list.getTeamId()));
                teamReturnResponses.add(teamResponse);
            }else {
                GetTeamResponse teamResponse = new GetTeamResponse(list.getTeamId(), list.getTeamName(), list.getTeamImgUrl(), teamDao.getTeamMemberCount(list.getTeamId()), true, teamDao.getBookmark(userId, list.getTeamId()));
                teamReturnResponses.add(teamResponse);
            }
        }

        return teamReturnResponses;
    }

}
*/
