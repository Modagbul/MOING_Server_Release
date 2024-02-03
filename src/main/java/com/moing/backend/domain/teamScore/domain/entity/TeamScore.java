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
        this.score += score;
    }

    public void updateLevel() {
        final int[] steps = {1, 2, 25, 45, 70, 120};

        for (int i = 5; i > 0; i--) {
            if (steps[i-1] <= this.level && this.level <= steps[i]) {
                if ((40 + ((i-1) * 15)) <= score) {
                    this.level+=1;
                    this.score -= (20 + ((i-1) * 15));
                    this.team.updateLevelOfFire();
                    return;
                }
            }
        }

    }
}
