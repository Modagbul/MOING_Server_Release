package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.boardComment.application.dto.request.CreateBoardCommentRequest;
import com.moing.backend.domain.boardComment.application.dto.response.CreateBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.mapper.BoardCommentMapper;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentSaveService;
import com.moing.backend.domain.team.application.service.CheckLeaderUseCase;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.utils.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBoardCommentUseCase {

    private final BoardCommentSaveService boardCommentSaveService;
    private final BaseBoardService baseBoardService;
    private final CheckLeaderUseCase checkLeaderUseCase;
    private final SendCommentAlarmUseCase sendCommentAlarmUseCase;
    /**
     * 게시글 댓글 생성
     */
    public CreateBoardCommentResponse createBoardComment(String socialId, Long teamId, Long boardId, CreateBoardCommentRequest createBoardCommentRequest) {
        // 1. 게시글 댓글 생성
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        boolean isLeader = checkLeaderUseCase.isTeamLeader(data.getMember(), data.getTeam());
        BoardComment boardComment = boardCommentSaveService.saveBoardComment(BoardCommentMapper.toBoardComment(data.getTeamMember(), data.getBoard(), createBoardCommentRequest, isLeader));
        // 2. 게시글 댓글 개수 증가
        data.getBoard().incrComNum();
        // 3. 게시글 댓글 알림
        sendCommentAlarmUseCase.sendNewUploadAlarm(data, boardComment);
        return new CreateBoardCommentResponse(boardComment.getBoardCommentId());
    }
}
