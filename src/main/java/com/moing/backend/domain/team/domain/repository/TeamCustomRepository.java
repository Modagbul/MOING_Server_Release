package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;

public interface TeamCustomRepository {
    GetTeamResponse findTeamByMemberId(Long memberId);
}
