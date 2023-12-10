package com.moing.backend.global.config.fcm.dto.event;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SingleFcmEvent {

    private String registrationToken;
    private String title;
    private String body;
    private Long memberId;
    private String idInfo;
    private String name;
    private AlarmType alarmType;
    private String path;
}
