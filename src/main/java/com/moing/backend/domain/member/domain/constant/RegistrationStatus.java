package com.moing.backend.domain.member.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegistrationStatus {
    UNCOMPLETED("회원가입을 합니다. 추가 정보를 입력하세요."),
    COMPLETED("로그인을 합니다.");
    private String message;
}
