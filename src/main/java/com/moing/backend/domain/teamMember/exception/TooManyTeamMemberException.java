package com.moing.backend.domain.teamMember.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class TooManyTeamMemberException extends TeamMemberException{
    public TooManyTeamMemberException(){
        super(ErrorCode.TOO_MANY_TEAM_MEMBER_ERROR,
                HttpStatus.FORBIDDEN);
    }
}
