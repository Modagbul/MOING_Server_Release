package com.moing.backend.domain.boardComment.domain.repository;

import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;

import java.util.List;
import java.util.Optional;

public interface BoardCommentCustomRepository {
    GetCommentResponse findBoardCommentAll(Long boardId, TeamMember teamMember);

    Optional<List<NewUploadInfo>> findNewUploadInfo(Long memberId, Long boardId);
}
