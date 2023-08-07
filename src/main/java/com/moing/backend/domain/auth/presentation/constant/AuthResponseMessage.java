package com.moing.backend.domain.auth.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthResponseMessage {
    SIGN_IN_SUCCESS("로그인을 했습니다"),
    SIGN_UP_SUCCESS("회원 가입을 했습니다"),
    REISSUE_TOKEN_SUCCESS("토큰을 재발급했습니다");
    private final String message;
}

