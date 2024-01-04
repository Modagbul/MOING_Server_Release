package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.missionState.domain.service.MissionStateDeleteService;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.missionState.domain.service.MissionStateSaveService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MissionStateUseCase {

    // 미션 종료 직전인지 확인
    // missionId, missionstate num mission.team.personum

    private final MissionQueryService missionQueryService;
    private final MissionStateQueryService missionStateQueryService;
    private final MissionStateSaveService missionStateSaveService;
    private final MissionStateDeleteService missionStateDeleteService;

    private final TeamScoreLogicUseCase teamScoreLogicUseCase;


    public boolean isAbleToEnd(Long missionId) {

        Mission mission = missionQueryService.findMissionById(missionId);
        Long total = totalPeople(mission);
        Long done = donePeople(mission);

        return done >= total;

    }
    public boolean isAbleToScoreUp(Long missionId) {

        Mission mission = missionQueryService.findMissionById(missionId);
        Long total = totalPeople(mission);
        Long done = donePeople(mission);

        log.info("done/total -> "+ done+ total);

        return done >= total;

    }


    public Long donePeople(Mission mission) {
        return Long.valueOf(missionStateQueryService.stateCountByMissionId(mission.getId()));
    }

    public Long totalPeople(Mission mission) {
        return Long.valueOf(mission.getTeam().getNumOfMember());

    }

    public void updateMissionState(Member member, Mission mission, MissionArchive missionArchive) {

        // 마지막 인증 시
        if (mission.getType().equals(MissionType.ONCE) && isAbleToEnd(mission.getId())) {
            mission.updateStatus(MissionStatus.SUCCESS);
        }
        missionStateSaveService.saveMissionState(member,mission, missionArchive.getStatus());

        if (isAbleToScoreUp(mission.getId())) {
            teamScoreLogicUseCase.updateTeamScore(mission.getId());
        }

    }


}
