package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.team.application.dto.response.TeamMemberInfo;

import java.util.List;
import java.util.Optional;

public interface TeamMemberCustomRepository {
    Optional<List<NewUploadInfo>> findNewUploadInfo(Long teamId, Long memberId);
    List<TeamMemberInfo> findTeamMemberInfoByTeamId(Long memberId, Long teamId);
}
