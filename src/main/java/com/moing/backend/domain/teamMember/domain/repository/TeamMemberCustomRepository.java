package com.moing.backend.domain.teamMember.domain.repository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberCustomRepository {
    List<Long> findMemberIdsByTeamId(Long teamId);
    Optional<List<String>> findFcmTokensByTeamIdAndMemberId(Long teamId, Long memberId);
    Optional<List<String>> findFcmTokensByTeamId(Long teamId);
}
