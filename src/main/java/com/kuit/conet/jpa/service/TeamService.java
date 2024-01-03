package com.kuit.conet.jpa.service;

import com.kuit.conet.common.exception.TeamException;
import com.kuit.conet.dto.web.request.team.ParticipateTeamRequest;
import com.kuit.conet.dto.web.request.team.TeamIdRequest;
import com.kuit.conet.dto.web.response.team.CreateTeamResponse;
import com.kuit.conet.dto.web.response.team.GetTeamResponse;
import com.kuit.conet.dto.web.response.team.ParticipateTeamResponse;
import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.team.TeamMember;
import com.kuit.conet.jpa.domain.team.*;

import com.kuit.conet.domain.storage.StorageDomain;
import com.kuit.conet.dto.web.request.team.CreateTeamRequest;
import com.kuit.conet.jpa.repository.TeamMemberRepository;
import com.kuit.conet.jpa.repository.TeamRepository;
import com.kuit.conet.jpa.repository.UserRepository;
import com.kuit.conet.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;;
import static com.kuit.conet.jpa.service.validator.TeamValidator.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    private final StorageService storageService;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    final int LEFT_LIMIT = 48;
    final int RIGHT_LIMIT = 122;
    final int TARGET_STRING_LENGTH = 8;


    public CreateTeamResponse createTeam(CreateTeamRequest teamRequest, HttpServletRequest httpRequest, MultipartFile file) {
        // 초대 코드 생성 및 코드 중복 확인
        String inviteCode = getRandomInviteCode();

        // 모임 생성 시간 찍기
        LocalDateTime codeGeneratedTime = LocalDateTime.now();

        // 팀 만든 멤버 정보 추출
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        Member teamCreator = userRepository.findById(userId);

        //이미지 s3 업로드
        String imgUrl = updateTeamImg(file);

        // team 생성
        Team newTeam = Team.createTeam(teamRequest.getTeamName(), inviteCode,codeGeneratedTime,teamCreator,imgUrl);
        teamRepository.save(newTeam);

        return new CreateTeamResponse(newTeam.getId(), newTeam.getInviteCode());
    }

    public ParticipateTeamResponse participateTeam(ParticipateTeamRequest teamRequest, HttpServletRequest httpRequest) {
        // 필요한 정보 조회
        String inviteCode = getInviteCodeFromRequest(teamRequest);
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        Member user = userRepository.findById(userId);
        Team team = teamRepository.findByInviteCode(inviteCode);

        // 모임에 이미 존재하는 회원인지 확인
        validateNewMemberInTeam(teamRepository,userId, team);

        // 초대 코드 생성 시간과 모임 참가 요청 시간 비교
        compareInviteCodeAndRequestTime(team);

        // team에 teamMember 추가 (변경 감지)
        TeamMember teamMember = TeamMember.createTeamMember(team, user);
        team.addTeamMember(teamMember);

        return new ParticipateTeamResponse(user.getName(), team.getName(), user.getStatus());
    }

    public List<GetTeamResponse> getTeam(HttpServletRequest httpRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));

        List<Team> teams = teamRepository.findByUserId(userId);

        // 모임의 created_at 시간 비교해서 3일 안지났으면 isNew 값 true, 지났으면 false로 반환
        return generateTeamReturnResponse(teams, userId);
    }

    public String leaveTeam(TeamIdRequest teamRequest, HttpServletRequest httpRequest) {
        Long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));
        Team team = teamRepository.findById(teamRequest.getTeamId());

        // 모임 존재 여부 확인
        if (team==null) {
            throw new TeamException(NOT_FOUND_TEAM);
        }

        //변경 감지 이용
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(team.getId(), userId);
        team.deleteMember(teamMember);

        return "모임 탈퇴에 성공하였습니다.";
    }


    private String getRandomInviteCode() {
        String inviteCode = generateInviteCode();
        while(true){
            if(validateDuplicateInviteCode(teamRepository, inviteCode))
                break;
            inviteCode = generateInviteCode();
        }
        return inviteCode;
    }

    private String updateTeamImg(MultipartFile file) {
        // 새로운 이미지 S3에 업로드
        String fileName = storageService.getFileName(file, StorageDomain.TEAM);
        String imgUrl = storageService.uploadToS3(file, fileName);
        return imgUrl;
    }

    public String generateInviteCode() {
        Random random = new Random();

        String generatedString = random.ints(LEFT_LIMIT, RIGHT_LIMIT+1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(TARGET_STRING_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    private String getInviteCodeFromRequest(ParticipateTeamRequest teamRequest) {
        String inviteCode = teamRequest.getInviteCode();
        validateInviteCodeExisting(teamRepository,inviteCode);
        return inviteCode;
    }

    private List<GetTeamResponse> generateTeamReturnResponse(List<Team> teams, Long userId) {
        List<GetTeamResponse> teamReturnResponses = new ArrayList<>();

        for(Team team : teams) {
            GetTeamResponse teamResponse;
            if(!isNewTeam(team)) {
                teamResponse = generateTeamResponse(team,userId, false);
            }else {
                teamResponse = generateTeamResponse(team,userId, true);
            }
            teamReturnResponses.add(teamResponse);
        }
        return teamReturnResponses;
    }

    private GetTeamResponse generateTeamResponse(Team team, Long userId, boolean b) {
        return new GetTeamResponse(team.getId(), team.getName(), team.getImgUrl(), teamRepository.getMemberCount(team.getId()),
                false, teamMemberRepository.isBookmark(userId, team.getId()));
    }

    //TODO: com.kuit.conet.service.TeamService 에 주석 처리된 코드 참고
    //      -> 해당 메서드에 대하여 JPA 변환 완료하면 com.kuit.conet.service.TeamService에서 (주석)코드 삭제하기

}
