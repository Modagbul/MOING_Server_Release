package com.moing.backend.domain.boardComment.domain.service;

import com.moing.backend.domain.boardComment.domain.repository.BoardCommentRepository;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.comment.domain.service.CommentSaveService;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BoardCommentSaveService implements CommentSaveService<BoardComment> {

    private final BoardCommentRepository boardCommentRepository;

    @Override
    public BoardComment saveComment(BoardComment boardComment){
        return this.boardCommentRepository.save(boardComment);
    }
}
