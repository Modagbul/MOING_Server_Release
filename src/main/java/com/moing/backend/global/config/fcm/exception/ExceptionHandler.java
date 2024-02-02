package com.moing.backend.global.config.fcm.exception;

import com.google.firebase.messaging.FirebaseMessagingException;

public class ExceptionHandler {

    public static NotificationException handleFirebaseMessagingException(FirebaseMessagingException e) {
        String errorCode = e.getErrorCode().name();
        String errorMessage = e.getMessage();

        switch (errorCode) {
            case "INVALID_ARGUMENT":
                return new NotificationException("올바르지 않은 인자 값입니다: " + errorMessage);
            case "NOT_FOUND":
                return new NotificationException("등록 토큰이 유효하지 않거나, 주제(Topic)가 존재하지 않습니다: " + errorMessage);
            case "UNREGISTERED":
                return new NotificationException("해당 주제(Topic)의 구독이 해지되었습니다: " + errorMessage);
            case "UNAVAILABLE":
                return new NotificationException("서비스를 사용할 수 없습니다: " + errorMessage);
            default:
                return new NotificationException("메시지 전송에 실패했습니다: " + errorMessage);
        }
    }
}

