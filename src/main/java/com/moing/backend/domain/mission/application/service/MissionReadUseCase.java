package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.global.util.SecurityUtils;
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

    public MissionReadRes getMission(String userSocialId, Long missionId) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);

        Mission mission = missionQueryService.findMissionById(missionId);
        return MissionMapper.mapToMissionReadRes(mission);
    }

    public String getTeamCategory(Long teamId) {
        Team team = teamGetService.getTeamByTeamId(teamId);
        return team.getCategory().name();
    }

}
