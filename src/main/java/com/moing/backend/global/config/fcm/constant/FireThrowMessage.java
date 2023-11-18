package com.moing.backend.global.config.fcm.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FireThrowMessage {

    NEW_FIRE_THROW_TITLE1("어라… 왜 이렇게 발등이 뜨겁지?\uD83E\uDD28"),
    NEW_FIRE_THROW_TITLE2("⚠\uFE0F불조심⚠\uFE0F "),


    NEW_FIRE_THROW_MESSAGE1("님에게 불을 던졌어요! 어서 미션을 인증해볼까요?"),
    NEW_FIRE_THROW_MESSAGE2("님이 던진 불에 타버릴지도 몰라요! 어서 인증하러갈까요?");



    private final String message;


}
