package com.moing.backend.domain.teamScore.application.service;


import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionState.application.service.MissionStateUseCase;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.moing.backend.domain.teamScore.domain.service.TeamScoreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamScoreLogicUseCase {

    private final TeamGetService teamGetService;
    private final MissionQueryService missionQueryService;
    private final TeamScoreQueryService teamScoreQueryService;
    private final MissionStateUseCase missionStateUseCase;

    public TeamScoreRes getTeamScoreInfo(Long teamId) {

        return TeamScoreRes.builder()
                .score(getScore(teamId))
                .level(getLevel(teamId))
                .build()
        ;
    }

    public Long updateSingleScore(Long missionId) {
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        TeamScore teamScore = teamScoreQueryService.findTeamScoreByTeam(team.getTeamId());

        teamScore.updateScore(missionStateUseCase.getScoreByMission(mission));
        teamScore.levelUp();
        return teamScore.getScore();
    }

    public Long getScore(Long teamId) {
        return teamScoreQueryService.findTeamScoreByTeam(teamId).getScore();
    }

    public Long getLevel(Long teamId) {
        return teamScoreQueryService.findTeamScoreByTeam(teamId).getLevel();
    }







}
