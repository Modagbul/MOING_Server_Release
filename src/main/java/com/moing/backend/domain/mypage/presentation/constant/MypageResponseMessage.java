package com.moing.backend.domain.mypage.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MypageResponseMessage {
    SIGN_OUT_SUCCESS("로그아웃을 했습니다"),
    WITHDRAWAL_SUCCESS("회원탈퇴를 했습니다"),
    GET_MYPAGE_SUCCESS("마이페이지를 조회했습니다"),
    GET_PROFILE_SUCCESS("프로필을 조회했습니다"),
    UPDATE_PROFILE_SUCCESS("프로필을 수정했습니다"),
    GET_ALARM_SUCCESS("알람 정보를 조회했습니다"),
    UPDATE_ALARM_SUCCESS("알람 정보를 수정했습니다");
    private final String message;
}
