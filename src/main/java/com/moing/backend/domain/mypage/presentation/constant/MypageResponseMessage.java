package com.moing.backend.domain.mypage.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MypageResponseMessage {
    SIGN_OUT_SUCCESS("로그아웃을 했습니다"),
    WITHDRAWAL_SUCCESS("회원탈퇴를 했습니다");
    private final String message;
}
