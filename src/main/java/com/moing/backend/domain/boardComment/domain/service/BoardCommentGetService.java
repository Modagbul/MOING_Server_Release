package com.moing.backend.domain.boardComment.domain.service;

import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.repository.BoardCommentRepository;
import com.moing.backend.domain.boardComment.exception.NotFoundByBoardCommentIdException;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class BoardCommentGetService {

    private final BoardCommentRepository boardCommentRepository;

    public BoardComment getBoardComment(Long boardCommentId){
        return boardCommentRepository.findBoardCommentByBoardCommentId(boardCommentId).orElseThrow(()->new NotFoundByBoardCommentIdException());
    }

    public GetBoardCommentResponse getBoardCommentAll(Long boardId, TeamMember teamMember){
        return boardCommentRepository.findBoardCommentAll(boardId, teamMember);
    }
}
