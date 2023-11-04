package com.moing.backend.domain.fire.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FireResponseMessage {
    THROW_FIRE_SUCCESS("불던지기를 완료 했습니다"),
    GET_RECEIVERS_SUCCESS("불 던질사람 조회를 완료 했습니다");

    private final String message;

}
