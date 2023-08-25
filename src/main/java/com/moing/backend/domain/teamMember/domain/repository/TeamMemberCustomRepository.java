package com.moing.backend.domain.teamMember.domain.repository;

import java.util.List;

public interface TeamMemberCustomRepository {
    List<Long> getMemberIdsByTeamId(Long teamId);
}
