package com.moing.backend.domain.auth.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NicknameDuplicationException extends AuthException {
    public NicknameDuplicationException(){
        super(ErrorCode.NICKNAME_DUPLICATION_ERROR,
                HttpStatus.BAD_REQUEST);
    }
}
