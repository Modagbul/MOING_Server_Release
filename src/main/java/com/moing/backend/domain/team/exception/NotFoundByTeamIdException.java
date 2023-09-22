package com.moing.backend.domain.team.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundByTeamIdException extends TeamException{
    public NotFoundByTeamIdException(){
        super(ErrorCode.NOT_FOUND_BY_TEAM_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
