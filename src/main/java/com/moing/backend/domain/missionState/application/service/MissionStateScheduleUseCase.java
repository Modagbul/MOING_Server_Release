package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.mission.application.service.MissionRemindAlarmUseCase;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.missionState.domain.service.MissionStateDeleteService;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@EnableScheduling // 스케줄링 활성화
@RequiredArgsConstructor
public class MissionStateScheduleUseCase {

    private final MissionStateUseCase missionStateUseCase;
    private final MissionRemindAlarmUseCase missionRemindAlarmUseCase;
    private final MissionQueryService missionQueryService;
    private final MissionStateQueryService missionStateQueryService;
    private final MissionStateDeleteService missionStateDeleteService;


    private final TeamScoreLogicUseCase teamScoreLogicUseCase;

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
        // 미션 state 에서 지우기 ( 이것도 대용량 )
        missionStateReset(ongoingRepeatMissions);
    }

    public void missionStateReset(List<Long> missionIds) {
        List<MissionState> missionStates = missionStateQueryService.findByMissionId(missionIds);
        missionStateDeleteService.deleteMissionState(missionStates);
    }


//    /**
//     * 단일 미션 마감
//     * 미션 단위 마감
//     * 한시간 마다 실행
//     */
//    public void singleMissionEndRoutineByMission() {
//
//        Mission mission = new Mission();
//        LocalDateTime now = LocalDateTime.now();
//
//        if (mission.getDueTo().isAfter(now)) {
//            mission.updateStatus(MissionStatus.END);
//            teamScoreLogicUseCase.updateTeamScore(mission.getId());
//        }
//    }

    /**
     * 단일 미션 마감
     * 해당 시간 미션 마감
     * 한시간 마다 실행
     */
    @Scheduled(cron = "0 1 * * * *")
    public void singleMissionEndRoutine() {

        List<Mission> missionByDueTo = missionQueryService.findMissionByDueTo();

        missionByDueTo.stream().forEach(mission -> {
            mission.updateStatus(MissionStatus.END);
            teamScoreLogicUseCase.updateTeamScore(mission.getId());
        });


    }


    /**
     * 미션 시작
     * 월요일 아침
     */
    @Scheduled(cron = "0 0 0 * * MON")
    public void RepeatMissionStart() {
        List<Mission> startMission = missionQueryService.findMissionByStatus(MissionStatus.WAIT);
        startMission.stream().forEach(
            mission -> mission.updateStatus(MissionStatus.ONGOING)
        );
    }


//    @Scheduled(cron = "8 0 0 * * *")
//    public void MissionRemindAlarm() {
//
//        missionRemindAlarmUseCase.sendRemindMissionAlarm();
//    }
}
