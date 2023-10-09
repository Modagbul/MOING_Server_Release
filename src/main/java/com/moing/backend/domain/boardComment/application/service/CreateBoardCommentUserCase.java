package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.boardComment.application.dto.request.CreateBoardCommentRequest;
import com.moing.backend.domain.boardComment.application.dto.response.CreateBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.mapper.BoardCommentMapper;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentSaveService;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.util.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardCommentUserCase {

    private final BoardCommentSaveService boardCommentSaveService;
    private final BoardCommentMapper boardCommentMapper;
    private final BaseBoardService baseBoardService;

    /**
     * 게시글 댓글 생성
     */
    public CreateBoardCommentResponse createBoardComment(String socialId, Long teamId, Long boardId, CreateBoardCommentRequest createBoardCommentRequest) {
        // 1. 게시글 댓글 생성
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        BoardComment boardComment = boardCommentSaveService.saveBoardComment(boardCommentMapper.toBoardComment(data.getTeamMember(), data.getBoard(), createBoardCommentRequest));
        // 2. 게시글 댓글 개수 증가
        data.getBoard().incrComNum();
        return new CreateBoardCommentResponse(boardComment.getBoardCommentId());
    }
}
