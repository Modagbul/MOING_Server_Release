package com.moing.backend.domain.teamScore.domain.service;

import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.moing.backend.domain.teamScore.domain.repository.TeamScoreRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@DomainService
@Transactional
@RequiredArgsConstructor
public class TeamScoreUpdateService {

    private final TeamScoreRepository teamScoreRepository;
    private final TeamGetService teamGetService;
    private final TeamScoreQueryService teamScoreQueryService;

    public TeamScore update(Long teamId, Long score) {
        Team team = teamGetService.getTeamByTeamId(teamId);

        TeamScore teamScore = teamScoreQueryService.findTeamScoreByTeam(team.getTeamId());

        teamScore.updateScore(score);

        return teamScore;

    }


}
