package com.moing.backend.global.config.fcm.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class InitializeException extends FirebaseException {
    public InitializeException() {
        super(ErrorCode.INITIALIZE_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
