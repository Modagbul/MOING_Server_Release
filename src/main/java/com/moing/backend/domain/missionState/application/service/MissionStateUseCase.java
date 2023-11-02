package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.missionState.domain.service.MissionStateSaveService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
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
    private final MissionStateSaveService missionStateSaveService;

    private final TeamScoreLogicUseCase teamScoreLogicUseCase;


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

    public void updateMissionState(Member member, Mission mission, MissionArchive missionArchive) {

        // 마지막 인증 시
        if (isAbleToEnd(mission.getId())) {
            mission.updateStatus(MissionStatus.SUCCESS);
            teamScoreLogicUseCase.updateSingleScore(mission.getId());
        }

        // 미션 인증 상태 저장
        missionStateSaveService.saveMissionState(member,mission, missionArchive.getStatus());




    }



}
