package com.moing.backend.domain.team.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class DeletedTeamException extends TeamException{
    public DeletedTeamException(){
        super(ErrorCode.DELETED_TEAM_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
