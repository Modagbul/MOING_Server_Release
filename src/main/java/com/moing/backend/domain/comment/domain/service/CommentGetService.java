package com.moing.backend.domain.comment.domain.service;

import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;

import java.util.List;
import java.util.Optional;

public interface CommentGetService<T> {
    T getComment(Long commentId);
    GetCommentResponse getCommentAll(Long boardId, TeamMember teamMember);
    Optional<List<NewUploadInfo>> getNewUploadInfo(Long memberId, Long boardId);
}
