package com.moing.backend.domain.boardComment.domain.repository;

import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;

public interface BoardCommentCustomRepository {
    GetBoardCommentResponse findBoardCommentAll(Long boardId, TeamMember teamMember);
}
