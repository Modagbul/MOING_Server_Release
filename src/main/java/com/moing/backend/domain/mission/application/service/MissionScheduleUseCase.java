package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.application.service.UpdateRemindAlarmUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
@Profile("prod")
public class MissionScheduleUseCase {

    private final MissionRemindAlarmUseCase missionRemindAlarmUseCase;
    private final MissionUpdateUseCase missionUpdateUseCase;
    private final UpdateRemindAlarmUseCase updateRemindAlarmUseCase;

    /**
     * 단일 미션 마감
     * 해당 시간 미션 마감
     * 한시간 마다 실행
     */
    @Scheduled(cron = "0 1 * * * *")
    public void singleMissionEndRoutine() {
        missionUpdateUseCase.terminateMissionByAdmin();
    }

    /**
     * 리마인드 알림
     * 인증하지 않은 미션이 있는 경우 알림
     * 매일 오후 8시
     */
    @Scheduled(cron = "0 0 20 * * *")
    public void MissionRemindAlarm() {
        missionRemindAlarmUseCase.sendRemindMissionAlarm();
    }

    @Scheduled(cron = "0 0 14 * * *")
    public void UpdateRemindAlarm() {
        updateRemindAlarmUseCase.sendUpdateAppPushAlarm();
    }
}