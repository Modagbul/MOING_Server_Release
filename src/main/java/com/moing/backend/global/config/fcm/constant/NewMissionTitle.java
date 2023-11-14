package com.moing.backend.global.config.fcm.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  NewMissionTitle {

    NEW_SINGLE_MISSION_COMING("의 새로운 미션이 등장했어요! "),
    NEW_REPEAT_MISSION_COMING("의 새로운 반복미션이 시작되었어요! ");
    private final String title;
}
