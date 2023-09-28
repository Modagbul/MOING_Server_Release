package com.moing.backend.domain.score.domain.service;

import com.moing.backend.domain.score.domain.entity.Score;
import com.moing.backend.domain.score.domain.repository.ScoreRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class ScoreSaveService {

    private final ScoreRepository scoreRepository;

    public Score save(Score score) {
        return scoreRepository.save(score);
    }

}
