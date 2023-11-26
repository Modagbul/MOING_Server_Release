package com.moing.backend.domain.team.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeamResponseMessage {
    CREATE_TEAM_SUCCESS("소모임을 생성하였습니다."),
    GET_TEAM_SUCCESS("홈 화면에서 내 소모임을 모두 조회했습니다."),
    GET_TEAM_DETAIL_SUCCESS("목표보드를 조회했습니다."),
    SIGNIN_TEAM_SUCCESS("소모임에 가입하였습니다."),
    GET_CURRENT_STATUS_SUCCESS("소모임 수정 전 조회했습니다."),
    REVIEW_TEAM_SUCCESS("소모임 삭제 전 조회했습니다."),
    DISBAND_TEAM_SUCCESS("[소모임장 권한] 소모임을 강제 종료했습니다."),
    UPDATE_TEAM_SUCCESS("[소모임장 권한] 소모임을 수정했습니다"),
    WITHDRAW_TEAM_SUCCESS("[소모임원 권한] 소모임을 탈퇴하였습니다"),
    SEND_APPROVAL_ALARM_SUCCESS("소모임들이 승인되었습니다.."),
    SEND_REJECTION_ALARM_SUCCESS("소모임들이 반려되었습니다."),
    GET_NEW_TEAM_SUCCESS("새로운 소모임들을 조회했습니다.");
    private final String message;
}
