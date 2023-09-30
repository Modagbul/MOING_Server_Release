package com.moing.backend.domain.boardComment.domain.service;

import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.repository.BoardCommentRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BoardCommentSaveService {

    private final BoardCommentRepository boardCommentRepository;

    public BoardComment saveBoardComment(BoardComment boardComment){
        return this.boardCommentRepository.save(boardComment);
    }
}
