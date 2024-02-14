package com.moing.backend.domain.teamScore.domain.entity;

import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@RequiredArgsConstructor
public class TeamScore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamScore_id")
    private Long id;

    @OneToOne
    private Team team;

    private Long score;

    private Long level;

    @Builder
    public TeamScore(Team team, Long score,Long level) {
        this.team = team;
        this.score = score;
        this.level = level;
    }

    public void updateScore(Long score) {

        int newStep = getStep(this.level, this.score + score);

        this.score += score;

        if (this.score < 0) { // 점수 차감 / score down + level down
            this.updateLevel(ScoreStatus.MINUS);
        }
        else { // 점수 획득. score up /  score down + level up

            if ((40 + (newStep * 15L) <= this.score)) { // score down + level up
                this.updateLevel(ScoreStatus.PLUS);

            } else { //  score up
//                this.score += score;
            }
        }


    }

    public int getStep(Long level, Long score) {
        final int[] steps = {1, 2, 26, 46, 71, 121};

        int index = 0;
        for (int i = 5; i > 0; i--) {
            if (steps[i-1] <= this.level && this.level < steps[i]) {
                index=i-1;
                break;

            }
        }
        return index;

    }

    public void updateLevel(ScoreStatus sign) {
        final int[] steps = {1, 2, 26, 46, 71, 121};

        this.level += sign.getValue();

        for (int i = 5; i > 0; i--) {
            if (steps[i-1] <= this.level && this.level <= steps[i]) {
                if ((40 + ((i-1) * 20)) <= score || score < 0) {
                    this.score -= sign.getValue() * (40 + ((i-1) * 20));
                    this.team.updateLevelOfFire();
                    return;
                }
            }
        }

    }
}
