package com.moing.backend.domain.teamScore.domain.entity;

import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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

    public void levelUp() {
        final int[] steps = {1, 2, 10, 25, 45, 100};
// 0부터 시작하기 때문에 무조건 0에서 걸림.

        for (int i = 5; i > 0; i--) {
            if (steps[i] <= this.level && this.level <= steps[i - 1]) {
                if (20 + (i-1) * 15 <= score) {
                    this.level+=1;
                    this.score -= (20 + (i-1) * 15);
                    return;
                }
            }
        }

    }
}
