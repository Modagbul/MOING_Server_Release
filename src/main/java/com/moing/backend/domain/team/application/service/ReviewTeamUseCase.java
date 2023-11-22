package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.team.application.dto.response.ReviewTeamResponse;
import com.moing.backend.domain.team.application.mapper.TeamMapper;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewTeamUseCase {

    private final TeamGetService teamGetService;
    private final TeamMapper teamMapper;
    private final MissionQueryService missionQueryService;


    public ReviewTeamResponse reviewTeam(String socialId, Long teamId){
        Team team=teamGetService.getTeamByTeamId(teamId);
        return teamMapper.toReviewTeamResponse(missionQueryService.findMissionsCountByTeam(team.getTeamId()),team);
    }
}
