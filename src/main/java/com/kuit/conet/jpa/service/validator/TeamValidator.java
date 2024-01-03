package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.common.exception.TeamException;
import com.kuit.conet.jpa.domain.team.Team;
import com.kuit.conet.jpa.repository.TeamRepository;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.EXIST_USER_IN_TEAM;
import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.NOT_FOUND_INVITE_CODE;

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
}
