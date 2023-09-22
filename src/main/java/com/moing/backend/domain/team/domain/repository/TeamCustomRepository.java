package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;

import java.util.Optional;

public interface TeamCustomRepository {
    GetTeamResponse findTeamByMemberId(Long memberId);
    Optional<Team> findTeamByTeamId(Long TeamId);
}
