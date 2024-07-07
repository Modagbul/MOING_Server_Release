package com.moing.backend.domain.missionComment.domain.service;

import com.moing.backend.domain.comment.domain.service.CommentSaveService;
import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.missionComment.domain.repository.MissionCommentRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MissionCommentSaveService implements CommentSaveService<MissionComment> {

    private final MissionCommentRepository missionCommentRepository;

    @Override
    public MissionComment saveComment(MissionComment comment) {
        return missionCommentRepository.save(comment);
    }
}
