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
public class TeamScoreQueryService {

    private final TeamScoreRepository teamScoreRepository;
    private final TeamGetService teamGetService;

    public TeamScore findTeamScoreByTeam(Long teamId) {

        return teamScoreRepository.findTeamScoreByTeam(teamId);
    }


}
