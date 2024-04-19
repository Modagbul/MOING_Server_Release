package com.moing.backend.domain.teamScore.domain.service;

import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamScore.domain.entity.ScoreStatus;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
public class TeamScoreUpdateService {

    private final int[] steps = {1, 2, 26, 46, 71};
    private final int STEP_NUM = 4;
    private final int BASE_NUM = 0;

    public void updateScore(TeamScore teamScore, Long newScore) {

        Long level = teamScore.getLevel();
        Long score = teamScore.getScore();
        int newStep = getStep(teamScore);

        score += newScore;

        if (score < 0) { // 점수 차감 / score down + level down
            updateLevel(teamScore,ScoreStatus.MINUS);
        }
        else { // 점수 획득. score up /  score down + level up

            if ((40 + (newStep * 15L) <= score)) { // score down + level up
                this.updateLevel(teamScore,ScoreStatus.PLUS);

            } else { //  score up
//                this.score += score;
            }
        }


    }

    public int getStep(TeamScore teamScore) {

        Long level = teamScore.getLevel();

        int index = STEP_NUM;
        for (int i = STEP_NUM; i > BASE_NUM; i--) {
            if (steps[i-1] <= level && level < steps[i]) {
                index=i-1;
                break;

            }
        }
        return index;

    }

    public void updateLevel(TeamScore teamScore, ScoreStatus sign) {

        Long score = teamScore.getScore();
        Long level = teamScore.getLevel();
        Team team = teamScore.getTeam();

        level += sign.getValue();
        teamScore.updateLevel(level);
        team.updateLevelOfFire(level.intValue());

        for (int i = STEP_NUM; i > BASE_NUM; i--) {
            if (steps[i-1] <= level && level <= steps[i]) {
                if ((40 + ((i-1) * 20)) <= score || score < 0) {
                    score -= sign.getValue() * (40 + ((i-1) * 20));
                    teamScore.updateScore(score);
                    // 변동되는지 확인
                    return;
                }
            }
        }

    }
}
