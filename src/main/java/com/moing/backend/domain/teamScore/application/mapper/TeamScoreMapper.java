package com.moing.backend.domain.teamScore.application.mapper;

import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.moing.backend.global.annotation.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TeamScoreMapper {

    public TeamScore mapToTeamScore(Team team) {
        return TeamScore.builder()
                .score(0L)
                .level(1L)
                .team(team)
                .build();
    }

}
