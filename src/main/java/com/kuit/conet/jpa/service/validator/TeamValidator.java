package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.common.exception.TeamException;
import com.kuit.conet.jpa.domain.team.Team;
import com.kuit.conet.jpa.repository.TeamMemberRepository;
import com.kuit.conet.jpa.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
public class TeamValidator {

    public static boolean validateDuplicateInviteCode(TeamRepository teamRepository, String inviteCode) {
        //inviteCode 써도되면 true, 중복되면 false 반환
        return !teamRepository.isExistInviteCode(inviteCode);
    }

    public static void validateInviteCodeExisting(TeamRepository teamRepository, String inviteCode) {
        if (!teamRepository.isExistInviteCode(inviteCode)) {
            throw new TeamException(NOT_FOUND_INVITE_CODE);
        }
    }

    public static void validateNewMemberInTeam(TeamRepository teamRepository, Long userId, Team team) {
        if (teamRepository.isExistUser(team.getId(), userId)) {
            throw new TeamException(EXIST_USER_IN_TEAM);
        }
    }

    public static void compareInviteCodeAndRequestTime(Team team) {
        LocalDateTime joinRequestTime = LocalDateTime.now();
        LocalDateTime generatedTime = team.getCodeGeneratedTime();
        LocalDateTime expirationDateTime = generatedTime.plusDays(1);

        //log.info("Team invite code generated time: {}", generatedTime);
        log.info("Team invite code expiration date time: {}", expirationDateTime);
        log.info("Team participation requested time: {}", joinRequestTime);

        if (joinRequestTime.isAfter(expirationDateTime)) {
            // 초대 코드 생성 시간으로부터 1일이 지났으면 exception
            log.error("유효 기간 만료: {}", EXPIRED_INVITE_CODE.getMessage());
            throw new TeamException(EXPIRED_INVITE_CODE);
        }
    }

    public static boolean isNewTeam(Team team) {
        log.info("{}", team.getName());
        LocalDateTime createdAt = team.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();

        return !(now.minusDays(3).isAfter(createdAt));
    }

    public static void validateTeamExisting(Team team) {
        if (team == null) {
            throw new TeamException(NOT_FOUND_TEAM);
        }
    }

    public static void isTeamMember(TeamMemberRepository teamMemberRepository, Team team, Long userId) {
        if (!teamMemberRepository.isTeamMember(team, userId)) {
            throw new TeamException(NOT_TEAM_MEMBER);
        }
    }
}
