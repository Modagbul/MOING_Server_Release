package com.moing.backend.domain.block.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundBlockException extends BlockException {
    public NotFoundBlockException() {
        super(ErrorCode.NOT_FOUND_BY_BOARD_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
