package com.moing.backend.domain.score.domain.repository;

import com.moing.backend.domain.score.domain.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("select s from Score s where s.teamId = :teamId ")
    Score findByTeamId(Long teamId);

}
