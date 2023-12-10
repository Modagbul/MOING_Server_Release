package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

import static com.moing.backend.global.config.fcm.constant.RemindMissionTitle.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionRemindAlarmUseCase {

    private final FcmService fcmService;

    public void sendRemindMissionAlarm(Mission mission) {



        Random random = new Random(System.currentTimeMillis());



        String title = getTitle(random.nextInt(4));
        String message = getMessage(random.nextInt(4));

        MultiRequest multiRequest = new MultiRequest();
    }

    public String getTitle(int num) {
        switch (num) {
            case 0:
                return REMIND_MISSION_TITLE1.getMessage();
            case 1:
                return REMIND_MISSION_TITLE2.getMessage();
            case 2:
                return REMIND_MISSION_TITLE3.getMessage();
            case 3:
                return REMIND_MISSION_TITLE4.getMessage();
        }
        return REMIND_MISSION_TITLE4.getMessage();

    }
    public String getMessage(int num) {
        switch (num) {
            case 0:
                return REMIND_MISSION_MESSAGE1.getMessage();
            case 1:
                return REMIND_MISSION_MESSAGE2.getMessage();
            case 2:
                return REMIND_MISSION_MESSAGE3.getMessage();
            case 3:
                return REMIND_MISSION_MESSAGE4.getMessage();
        }
        return REMIND_MISSION_MESSAGE4.getMessage();

    }

}
