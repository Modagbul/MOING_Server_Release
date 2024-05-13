package com.moing.backend.domain.missionComment.domain.service;

import com.moing.backend.domain.comment.domain.service.CommentDeleteService;
import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.missionComment.domain.repository.MissionCommentRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MissionCommentDeleteService implements CommentDeleteService<MissionComment> {

    private final MissionCommentRepository missionCommentRepository;
    @Override
    public void deleteComment(MissionComment comment) {
        missionCommentRepository.delete(comment);
    }
}
