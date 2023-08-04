package com.moing.backend.global.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(false, "400", "올바른 패턴을 입력하세요."),
    METHOD_NOT_ALLOWED(false,"405", "클라이언트가 사용한 HTTP 메서드가 리소스에서 허용되지 않습니다."),
    INTERNAL_SERVER_ERROR(false,"500", "서버에서 요청을 처리하는 동안 오류가 발생했습니다.");

    private Boolean isSuccess;
    private String errorCode;
    private String message;

}