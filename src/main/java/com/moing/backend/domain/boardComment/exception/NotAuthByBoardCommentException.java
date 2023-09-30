package com.moing.backend.domain.boardComment.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotAuthByBoardCommentException extends BoardCommentException {
    public NotAuthByBoardCommentException() {
        super(ErrorCode.NOT_AUTH_BY_BOARD_COMMENT_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
