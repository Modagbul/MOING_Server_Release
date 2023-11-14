package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Random;

import static com.moing.backend.global.config.fcm.constant.RemindMissionTitle.*;
import static java.lang.Math.random;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionRemindAlarmUseCase {

    private final FcmService fcmService;

    public void sendRemindMissionAlarm(Mission mission) {

        Random random = new Random(System.currentTimeMillis());

        String title = getTitle("receiver",random.nextInt(4));
        String message = getMessage(mission.getTitle(),random.nextInt(4));

        MultiRequest multiRequest = new MultiRequest();
    }

    public String getTitle(String receiver, int num) {
        switch (num) {
            case 0:
                return receiver + REMIND_MISSION_TITLE1.getMessage();
            case 1:
                return receiver + REMIND_MISSION_TITLE2.getMessage();
            case 2:
                return receiver + REMIND_MISSION_TITLE3.getMessage();
            case 3:
                return receiver + REMIND_MISSION_TITLE4.getMessage();
        }
        return receiver + REMIND_MISSION_TITLE4.getMessage();

    }
    public String getMessage(String missionTitle, int num) {
        switch (num) {
            case 0:
                return missionTitle + REMIND_MISSION_MESSAGE1.getMessage();
            case 1:
                return missionTitle + REMIND_MISSION_MESSAGE2.getMessage();
            case 2:
                return missionTitle + REMIND_MISSION_MESSAGE3.getMessage();
            case 3:
                return missionTitle + REMIND_MISSION_MESSAGE4.getMessage();
        }
        return missionTitle + REMIND_MISSION_MESSAGE4.getMessage();

    }

}
