package com.moing.backend.domain.boardComment.domain.service;

import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.repository.BoardCommentRepository;
import com.moing.backend.domain.boardComment.exception.NotFoundByBoardCommentIdException;
import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.comment.domain.service.CommentGetService;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class BoardCommentGetService implements CommentGetService<BoardComment> {

    private final BoardCommentRepository boardCommentRepository;

    @Override
    public BoardComment getComment(Long boardCommentId){
        return boardCommentRepository.findBoardCommentByBoardCommentId(boardCommentId).orElseThrow(NotFoundByBoardCommentIdException::new);
    }

    @Override
    public GetCommentResponse getCommentAll(Long boardId, TeamMember teamMember){
        return boardCommentRepository.findBoardCommentAll(boardId, teamMember);
    }

    @Override
    public Optional<List<NewUploadInfo>> getNewUploadInfo(Long memberId, Long boardId) {
        return boardCommentRepository.findNewUploadInfo(memberId, boardId);
    }
}
