package com.moing.backend.global.config.fcm.dto.request;

import com.moing.backend.domain.history.domain.entity.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleRequest {

    private String registrationToken;

    private String title;

    private String body;

    private Long memberId;

    private String idInfo;

    private String name;

    private AlarmType alarmType;

    private String path;

}
