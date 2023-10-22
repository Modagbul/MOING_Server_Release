package com.moing.backend.domain.teamScore.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamScoreLogicUseCase {

    public Long getScore(Long teamLevel) {
        return 1L;
    }
}
