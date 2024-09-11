package com.moing.backend.domain.teamScore.application.service;


import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.moing.backend.domain.teamScore.domain.service.TeamScoreQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeamScoreGetUseCase {

    private final TeamScoreQueryService teamScoreQueryService;

    public TeamScoreRes getTeamScoreInfo(Long teamId) {

        TeamScore teamScore = teamScoreQueryService.findTeamScoreByTeam(teamId);
        Long level = teamScore.getLevel();
        Long score = teamScore.getScore();


        return TeamScoreRes.builder()
                .score(score%100)
                .level(level)
                .build();

    }

}
