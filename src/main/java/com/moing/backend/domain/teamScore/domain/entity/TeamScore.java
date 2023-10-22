package com.moing.backend.domain.teamScore.domain.entity;

import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
public class TeamScore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @OneToOne
    private Team team;

    private Long score;

    @Builder
    public TeamScore(Team team, Long score) {
        this.team = team;
        this.score = score;
    }

    public void updateScore(Long score) {
        this.score = score;
    }
}
