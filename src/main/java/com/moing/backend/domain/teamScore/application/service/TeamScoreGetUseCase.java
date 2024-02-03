package com.moing.backend.domain.teamScore.application.service;


import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.team.domain.service.TeamSaveService;
import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.moing.backend.domain.teamScore.domain.service.TeamScoreQueryService;
import com.moing.backend.domain.teamScore.domain.service.TeamScoreSaveService;
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

        return TeamScoreRes.builder()
                .score(getScore(teamId))
                .level(getLevel(teamId))
                .build()
        ;
    }

    private Long getScore(Long teamId) {
        return teamScoreQueryService.findTeamScoreByTeam(teamId).getScore();
    }

    private Long getLevel(Long teamId) {
        return teamScoreQueryService.findTeamScoreByTeam(teamId).getLevel();
    }



}
