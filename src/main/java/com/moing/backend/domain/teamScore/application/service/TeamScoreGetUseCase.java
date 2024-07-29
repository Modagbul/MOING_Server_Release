package com.moing.backend.domain.teamScore.application.service;


import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.domain.entity.ScoreStatus;
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
                .score((long) (score / getMaxScore(level) * 100))
                .level(level)
                .build();

    }

    public Float getMaxScore(Long level) {
        final int[] steps = {1, 2, 26, 46, 71};
        final float[] maxScores = {40, 60, 80, 100, 120};

        int index = 0;
        int stepSize = steps.length - 1;

        for (int i = stepSize; i > 0; i--) {
            if (steps[stepSize] <= level) {
                index = stepSize;
            }

            else if (steps[i-1] <= level && level < steps[i]) {
                index=i-1;
                break;

            }
        }
        return maxScores[index];

    }
}
