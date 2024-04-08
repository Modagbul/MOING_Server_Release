package com.moing.backend.domain.teamScore.application.service;


import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionState.domain.service.MissionArchiveStateQueryService;
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
@RequiredArgsConstructor
public class TeamScoreLogicUseCase {

    private final MissionQueryService missionQueryService;
    private final TeamScoreQueryService teamScoreQueryService;
    private final MissionArchiveStateQueryService missionArchiveStateQueryService;

    public TeamScoreRes getTeamScoreInfo(Long teamId) {

        return TeamScoreRes.builder()
                .score(getScore(teamId))
                .level(getLevel(teamId))
                .build()
        ;
    }

    @Transactional
    public Long updateTeamScore(Long missionId) {
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();
        TeamScore teamScore = teamScoreQueryService.findTeamScoreByTeam(team.getTeamId());

        teamScore.updateScore(getScoreByMission(mission));
        teamScore.levelUp();

        return teamScore.getScore();
    }

    public Long getScore(Long teamId) {
        return teamScoreQueryService.findTeamScoreByTeam(teamId).getScore();
    }

    public Long getLevel(Long teamId) {
        return teamScoreQueryService.findTeamScoreByTeam(teamId).getLevel();
    }

    public Long getScoreByMission(Mission mission) {
        float total = totalPeople(mission);
        float done = donePeople(mission);

        if (done == 0) {
            return 0L;
        } else {
            return (long) ((done / total * 100) / 5);
        }

    }

    public float donePeople(Mission mission) {
        return Float.valueOf(missionArchiveStateQueryService.stateCountByMissionId(mission.getId()));
    }

    public float totalPeople(Mission mission) {
        return Float.valueOf(mission.getTeam().getNumOfMember());

    }




}
