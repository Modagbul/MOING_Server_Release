package com.moing.backend.domain.team.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotAuthException extends TeamException{
    public NotAuthException(){
        super(ErrorCode.NOT_AUTH_BY_TEAM_ERROR,
                HttpStatus.UNAUTHORIZED);
    }
}
