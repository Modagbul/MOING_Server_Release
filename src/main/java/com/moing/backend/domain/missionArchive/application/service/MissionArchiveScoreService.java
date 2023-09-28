package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.score.domain.entity.Score;
import com.moing.backend.domain.score.domain.service.ScoreQueryService;
import com.moing.backend.domain.score.domain.service.ScoreSaveService;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionArchiveScoreService {

    private final ScoreQueryService scoreQueryService;

    public void addScore(Team team) {
        // steps
        // 단계별
        Score score = scoreQueryService.findByTeamId(team.getTeamId());
        score.updateScore(20);

    }

}
