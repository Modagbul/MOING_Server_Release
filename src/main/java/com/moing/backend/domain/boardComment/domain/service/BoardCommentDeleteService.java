package com.moing.backend.domain.boardComment.domain.service;

import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.repository.BoardCommentRepository;
import com.moing.backend.domain.comment.domain.service.CommentDeleteService;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BoardCommentDeleteService implements CommentDeleteService<BoardComment> {
    private final BoardCommentRepository boardCommentRepository;

    @Override
    public void deleteComment(BoardComment boardComment){
        this.boardCommentRepository.delete(boardComment);
    }

}
