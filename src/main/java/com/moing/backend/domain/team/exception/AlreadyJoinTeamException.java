package com.moing.backend.domain.team.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class AlreadyJoinTeamException extends TeamException{
    public AlreadyJoinTeamException(){
        super(ErrorCode.ALREADY_JOIN_ERROR,
                HttpStatus.UNAUTHORIZED);
    }
}
