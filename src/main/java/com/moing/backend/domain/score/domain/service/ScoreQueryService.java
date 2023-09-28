package com.moing.backend.domain.score.domain.service;

import com.moing.backend.domain.score.domain.entity.Score;
import com.moing.backend.domain.score.domain.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreQueryService {

    private final ScoreRepository scoreRepository;

    public Score findByTeamId(Long teamId) {
        return scoreRepository.findByTeamId(teamId);
    }

}
