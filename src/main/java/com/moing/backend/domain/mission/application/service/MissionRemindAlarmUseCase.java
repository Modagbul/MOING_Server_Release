package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveScheduleQueryService;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.moing.backend.global.config.fcm.constant.RemindMissionTitle.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionRemindAlarmUseCase {

    private final MissionArchiveScheduleQueryService missionArchiveScheduleQueryService;
    private final ApplicationEventPublisher eventPublisher;


    public Boolean sendRemindMissionAlarm() {

        Random random = new Random(System.currentTimeMillis());
        String title = getTitle(random.nextInt(4));
        String message = getMessage(random.nextInt(4));

        String REMIND_NAME = "미션 리마인드";

        Optional<List<MemberIdAndToken>> remainMissionPeople = missionArchiveScheduleQueryService.getRemainMissionPeople();

        eventPublisher.publishEvent(new MultiFcmEvent(title, message, remainMissionPeople, remainMissionPeople,
                "",REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue()));
        return true;
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
