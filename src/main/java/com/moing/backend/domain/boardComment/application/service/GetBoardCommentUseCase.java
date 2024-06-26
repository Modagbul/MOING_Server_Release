package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.boardComment.domain.service.BoardCommentGetService;
import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.utils.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetBoardCommentUseCase {

    private final BoardCommentGetService boardCommentGetService;
    private final BaseBoardService baseBoardService;

    /**
     * 게시글 댓글 전체 조회
     */
    public GetCommentResponse getBoardCommentAll(String socialId, Long teamId, Long boardId){
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        return boardCommentGetService.getCommentAll(boardId, data.getTeamMember());
    }
}
