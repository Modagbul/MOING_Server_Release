package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.res.MissionConfirmRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionRead.application.service.CreateMissionReadUseCase;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionReadUseCase {

    private final MissionQueryService missionQueryService;
    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final CreateMissionReadUseCase createMissionReadUseCase;

    public MissionReadRes getMission(String userSocialId, Long missionId) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        return missionQueryService.findMissionByIds(member.getMemberId(),missionId);
    }

    public String getTeamCategory(Long teamId) {
        Team team = teamGetService.getTeamByTeamId(teamId);
        return team.getCategory();
    }

    public MissionConfirmRes confirmMission(String socialId, Long missionId, Long teamId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        Team team=teamGetService.getTeamByTeamId(teamId);
        Mission mission=missionQueryService.findMissionById(missionId);
        createMissionReadUseCase.createMissionRead(team, member, mission);
        return new MissionConfirmRes(mission.getId());
    }
}
