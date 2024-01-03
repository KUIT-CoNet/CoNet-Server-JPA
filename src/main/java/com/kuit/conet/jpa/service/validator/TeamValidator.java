package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.jpa.repository.TeamRepository;

public class TeamValidator {

    public static boolean validateDuplicateInviteCode(TeamRepository teamRepository, String inviteCode) {
        //inviteCode 써도되면 true, 중복되면 false 반환
        return !teamRepository.isExistInviteCode(inviteCode);
    }
}
