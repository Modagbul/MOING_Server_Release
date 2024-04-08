package com.moing.backend.domain.missionState.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionState.domain.service.MissionArchiveStateQueryService;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MissionStateUseCase {

    // 미션 종료 직전인지 확인
    // missionId, missionstate num mission.team.personum

    private final MissionArchiveStateQueryService missionArchiveStateQueryService;
    private final TeamScoreLogicUseCase teamScoreLogicUseCase;


    /*
     모든 모임원이 미션을 완료했는지 여부 확인
     */
    public boolean isAbleToEnd(Mission mission) {

        Long total = totalPeople(mission);
        Long done = donePeople(mission);

        return done > total;

    }

    public Long donePeople(Mission mission) {
        return Long.valueOf(missionArchiveStateQueryService.stateCountByMissionId(mission.getId()));
    }

    public Long totalPeople(Mission mission) {
        return Long.valueOf(mission.getTeam().getNumOfMember());
    }

    public void updateMissionState(Member member, Mission mission, MissionArchive missionArchive) {

        MissionType missionType = mission.getType();
        Long missionId = mission.getId();

//        missionStateSaveService.saveMissionState(member,mission, missionArchive.getStatus());

        if (missionType == MissionType.ONCE) {

            if (isAbleToEnd(mission)) {
                mission.updateStatus(MissionStatus.SUCCESS);
                teamScoreLogicUseCase.updateTeamScore(missionId);
            }

        }

    }


}
