package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.mission.application.service.MissionRemindAlarmUseCase;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@EnableAsync
@EnableScheduling // 스케줄링 활성화
@RequiredArgsConstructor
@Profile("prod")
public class MissionStateScheduleUseCase {

    private final MissionRemindAlarmUseCase missionRemindAlarmUseCase;
    private final MissionQueryService missionQueryService;

    /**
     * 단일 미션 마감
     * 해당 시간 미션 마감
     * 한시간 마다 실행
     */
    @Scheduled(cron = "0 1 * * * *")
    public void singleMissionEndRoutine() {

        List<Mission> missionByDueTo = missionQueryService.findMissionByDueTo();

        for (Mission mission : missionByDueTo) {
            mission.updateStatus(MissionStatus.END);
        }

    }


    @Scheduled(cron = "0 0 20 * * *")
    public void MissionRemindAlarm() {
        missionRemindAlarmUseCase.sendRemindMissionAlarm();
    }


//    @Scheduled(cron = "0 0 17 * * *")
//    public void UpdatePushAlarm() {
//        updateRemindAlarmUseCase.sendUpdateAppPushAlarm();
//    }

}
