package com.moing.backend.domain.team.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class AlreadyWithdrawTeamException extends TeamException{
    public AlreadyWithdrawTeamException(){
        super(ErrorCode.ALREADY_WITHDRAW_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
