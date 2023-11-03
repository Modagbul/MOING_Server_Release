package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.missionState.domain.service.MissionStateDeleteService;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MissionStateScheduleUseCase {

    private final MissionStateUseCase missionStateUseCase;
    private final MissionQueryService missionQueryService;
    private final MissionStateQueryService missionStateQueryService;
    private final MissionStateDeleteService missionStateDeleteService;

    private final TeamScoreLogicUseCase teamScoreLogicUseCase;

    // 스케쥴러에 쓰면 됨.
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

    // 한시간마다 실행
    public void singleMissionEndRoutine() {

        Mission mission = new Mission();

        LocalDateTime now = LocalDateTime.now();

        if (mission.getDueTo().isAfter(now)) {
            mission.updateStatus(MissionStatus.END);
        }
    }
}
