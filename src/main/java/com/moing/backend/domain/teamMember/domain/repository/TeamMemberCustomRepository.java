package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.application.dto.response.TeamMemberInfo;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;

import java.util.List;
import java.util.Optional;

public interface TeamMemberCustomRepository {
    List<Long> findMemberIdsByTeamId(Long teamId);
    Optional<List<String>> findFcmTokensByTeamIdAndMemberId(Long teamId, Long memberId);
    Optional<List<String>> findFcmTokensByTeamId(Long teamId);
    List<TeamMemberInfo> findTeamMemberInfoByTeamId(Long teamId);
    List<TeamMember> findTeamMemberByMemberId(Long memberId);
}
