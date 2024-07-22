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
                .score(score / getMaxScore(level) * 100)
                .level(level)
                .build();

    }

    public Long getMaxScore(Long level) {
        final int[] steps = {1, 2, 26, 46, 71};
        final long[] maxScores = {40, 60, 80, 100, 120};

        int index = 0;
        int stepSize = steps.length - 1;

        for (int i = stepSize; i > 0; i--) {
            System.out.println(steps[i-1]+" "+level+" "+steps[i]);
            if (steps[stepSize] <= level) {
                index = stepSize;
            }

            else if (steps[i-1] <= level && level < steps[i]) {
                index=i-1;
                break;

            }
        }
        System.out.println(maxScores[index]);
        return maxScores[index];

    }
//
//    public void updateScore(Long score) {
//
//        int newStep = getStep(this.level, this.score + score);
//
//        this.score += score;
//
//        if (this.score < 0) { // 점수 차감 / score down + level down
//            this.updateLevel(ScoreStatus.MINUS);
//        }
//        else { // 점수 획득. score up /  score down + level up
//
//            if ((40 + (newStep * 15L) <= this.score)) { // score down + level up
//                this.updateLevel(ScoreStatus.PLUS);
//
//            } else { //  score up
////                this.score += score;
//            }
//        }


//    }

}
