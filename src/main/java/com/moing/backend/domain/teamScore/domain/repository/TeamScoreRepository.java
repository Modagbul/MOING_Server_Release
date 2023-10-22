package com.moing.backend.domain.teamScore.domain.repository;

import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamScoreRepository extends JpaRepository<TeamScore, Long> , TeamScoreCustomRepository{
}