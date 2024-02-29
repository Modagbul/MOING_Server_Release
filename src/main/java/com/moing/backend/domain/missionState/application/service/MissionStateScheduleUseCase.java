package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.member.application.service.UpdateRemindAlarmUseCase;
import com.moing.backend.domain.mission.application.service.MissionRemindAlarmUseCase;
import com.moing.backend.domain.mission.application.service.SendMissionStartAlarmUseCase;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.missionState.domain.service.MissionStateDeleteService;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@EnableAsync
@EnableScheduling // 스케줄링 활성화
@RequiredArgsConstructor
@Profile("prod")
public class MissionStateScheduleUseCase {

    private final MissionStateUseCase missionStateUseCase;
    private final MissionRemindAlarmUseCase missionRemindAlarmUseCase;
    private final MissionQueryService missionQueryService;
    private final UpdateRemindAlarmUseCase updateRemindAlarmUseCase;

    private final TeamScoreLogicUseCase teamScoreLogicUseCase;

    private final SendMissionStartAlarmUseCase sendMissionStartAlarmUseCase;


    /**
     * 반복미션 마감
     * 일요일 마감 루틴
     */
    @Scheduled(cron = "0 59 23 * * SUN")
    public void sundayRepeatMissionRoutine() {

        // 모든 진행중인 반복 미션 모아서
        List<Long> ongoingRepeatMissions = missionQueryService.findOngoingRepeatMissions();

        // 팀 스코어 반영 ( 배치 처리 해야하는데 )
        for (Long id : ongoingRepeatMissions) {
            teamScoreLogicUseCase.updateTeamScore(id);
        }
    }


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
            teamScoreLogicUseCase.updateTeamScore(mission.getId());
        }

    }

    /**
     * 미션 시작
     * 월요일 아침
     */
    @Scheduled(cron = "0 0 0 * * MON")
    public void RepeatMissionStart() {
        List<Mission> startMission = missionQueryService.findRepeatMissionByStatus(MissionStatus.WAIT);
        startMission.forEach(
            mission -> {
                // 미션 시작 알림
                sendMissionStartAlarmUseCase.sendRepeatMissionStartAlarm(mission);
                mission.updateStatus(MissionStatus.ONGOING);
            }
        );
    }


    @Scheduled(cron = "0 0 8 * * *")
    public void MissionRemindAlarm() {
        missionRemindAlarmUseCase.sendRemindMissionAlarm();
    }
    @Scheduled(cron = "0 1 20 * * 0")
    public void RepeatMissionRemindAlarmOnSunday() {
        missionRemindAlarmUseCase.sendRepeatMissionRemindOnSunday();
    }

    @Scheduled(cron = "0 1 20 * * 1")
    public void RepeatMissionRemindAlarmOnMonday() {
        missionRemindAlarmUseCase.sendRepeatMissionRemindOnMonday();
    }

    @Scheduled(cron = "0 0 17 * * *")
    public void UpdatePushAlarm() {
        updateRemindAlarmUseCase.sendUpdateAppPushAlarm();
    }


}
