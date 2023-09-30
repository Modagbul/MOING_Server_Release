package com.moing.backend.domain.board.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundByBoardIdException extends BoardException {
    public NotFoundByBoardIdException() {
        super(ErrorCode.NOT_FOUND_BY_BOARD_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
