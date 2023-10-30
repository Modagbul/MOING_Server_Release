package com.moing.backend.domain.teamScore.application.service;


import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionState.application.service.MissionStateUseCase;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
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

    public Long getScore(Long teamLevel) {
        return 1L;
    }

    public Integer getLevel(Long teamId) {
        return teamGetService.getTeamByTeamId(teamId).getLevelOfFire();
    }

    public Long updateSingleScore(Long missionId) {
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();
        TeamScore teamScore = teamScoreQueryService.findTeamScoreByTeam(team.getTeamId());
        teamScore.updateScore(missionStateUseCase.getScoreByMission(mission));
        teamScore.levelUp();
        return teamScore.getScore();
    }







}
