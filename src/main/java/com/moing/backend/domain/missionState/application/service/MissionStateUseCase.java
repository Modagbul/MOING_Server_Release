package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionStateUseCase {

    // 미션 종료 직전인지 확인
    // missionId, missionstate num mission.team.personum

    private final MissionQueryService missionQueryService;
    private final MissionStateQueryService missionStateQueryService;


    public boolean isAbleToEnd(Long missionId) {

        Mission mission = missionQueryService.findMissionById(missionId);
        Long total = totalPeople(mission);
        Long done = donePeople(mission);

        return done == total-1;

    }

    public Long getScoreByMission(Mission mission) {
        Long total = totalPeople(mission);
        Long done = donePeople(mission);

        return (done / total * 100) / 5 ;

    }

    public Long donePeople(Mission mission) {
        return missionStateQueryService.stateCountByMissionId(mission.getId());
    }

    public Long totalPeople(Mission mission) {
        return Long.valueOf(mission.getTeam().getNumOfMember());

    }



}
