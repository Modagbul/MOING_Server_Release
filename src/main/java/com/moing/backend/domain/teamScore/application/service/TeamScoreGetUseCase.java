package com.moing.backend.domain.teamScore.application.service;


import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.moing.backend.domain.teamScore.domain.service.TeamScoreQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeamScoreGetUseCase {

    private final TeamScoreQueryService teamScoreQueryService;

    public TeamScoreRes getTeamScoreInfo(Long teamId) {

        TeamScore teamScore = teamScoreQueryService.findTeamScoreByTeam(teamId);
        Long level = teamScore.getLevel();
        Long score = teamScore.getScore();

        // 70 레벨 이상은 경험치 120 되어야 레벨업 가능. level을 각 레벨 별 필요한 경험치 수에 따르 퍼센트로 계산
        if (level > 70) {
            score = ( score / 120 ) * 100;
        }

        return TeamScoreRes.builder()
                .score(score)
                .level(level)
                .build();

    }

}
