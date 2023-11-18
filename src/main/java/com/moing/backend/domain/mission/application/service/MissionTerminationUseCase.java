package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionTerminationUseCase {

    private final MemberGetService memberGetService;
    private final MissionQueryService missionQueryService;

    // 스케쥴러에서 호출
    public void terminateMission() {

        List<Mission> missionByDueTo = missionQueryService.findMissionByDueTo();

        missionByDueTo.stream().forEach(
                // 미션 종료 처리
                mission -> mission.updateStatus(MissionStatus.END)

        );
    }

    // 미션 점수 반영 -> MissionState



    // MissionState 조회 해서 미션 점수 현황조회

    public void getMissionScoreStatus() {

    }

    // 팀별 점수 반영
    public void updateMissionScore() {

    }

}
