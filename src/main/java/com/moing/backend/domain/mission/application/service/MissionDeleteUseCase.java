package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.mission.domain.service.MissionDeleteService;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.mission.exception.NoAccessCreateMission;
import com.moing.backend.domain.mission.exception.NoAccessDeleteMission;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.response.BaseServiceResponse;
import com.moing.backend.global.utils.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionDeleteUseCase {

    private final MissionDeleteService missionDeleteService;
    private final MemberGetService memberGetService;

    private final BaseService baseService;
    private final MissionQueryService missionQueryService;

    public Long deleteMission(String userSocialId,Long missionId) {


        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        Long memberId = member.getMemberId();

        if (!memberId.equals(mission.getMakerId()) || memberId.equals(team.getLeaderId())) {
            throw new NoAccessDeleteMission();
        }
        return missionDeleteService.deleteMission(missionId);
    }

}
