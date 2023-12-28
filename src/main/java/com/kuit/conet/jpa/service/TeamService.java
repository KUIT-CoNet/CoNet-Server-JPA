package com.kuit.conet.jpa.service;

import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.team.TeamMember;
import com.kuit.conet.jpa.domain.team.*;

import com.kuit.conet.domain.storage.StorageDomain;
import com.kuit.conet.dto.request.team.CreateTeamRequest;
import com.kuit.conet.dto.response.team.*;
import com.kuit.conet.jpa.repository.TeamMemberRepository;
import com.kuit.conet.jpa.repository.TeamRepository;
import com.kuit.conet.jpa.repository.UserRepository;
import com.kuit.conet.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final StorageService storageService;
//    private final TeamDao teamDao;
//    private final UserDao userDao;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    public CreateTeamResponse createTeam(CreateTeamRequest createTeamRequest, HttpServletRequest httpRequest, MultipartFile file) {
        String inviteCode;

        // 초대 코드 생성 및 코드 중복 확인
        do {
            inviteCode = generateInviteCode();
        } while (!teamRepository.findByInviteCode(inviteCode).isEmpty());  // 중복되면 true 반환

        // 모임 생성 시간 찍기
        Timestamp codeGeneratedTime = Timestamp.valueOf(LocalDateTime.now());

        // 팀 만든 멤버 정보 추출
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        Member teamCreator = userRepository.findById(userId);

        //이미지 s3 업로드
        String imgUrl = updateTeamImg(file);

        // team 생성
        Team newTeam = Team.createTeam(createTeamRequest.getTeamName(), inviteCode,codeGeneratedTime,teamCreator,imgUrl);
        teamRepository.save(newTeam);

        return new CreateTeamResponse(newTeam.getId(), newTeam.getInviteCode());
    }

    private String updateTeamImg(MultipartFile file) {
        // 새로운 이미지 S3에 업로드
        String fileName = storageService.getFileName(file, StorageDomain.TEAM);
        String imgUrl = storageService.uploadToS3(file, fileName);
        return imgUrl;
    }

    public String generateInviteCode() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit+1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    //TODO: com.kuit.conet.service.TeamService 에 주석 처리된 코드 참고
    //      -> 해당 메서드에 대하여 JPA 변환 완료하면 com.kuit.conet.service.TeamService에서 (주석)코드 삭제하기

}
