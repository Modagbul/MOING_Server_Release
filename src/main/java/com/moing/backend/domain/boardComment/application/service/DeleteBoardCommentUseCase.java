package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentDeleteService;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentGetService;
import com.moing.backend.domain.boardComment.exception.NotAuthByBoardCommentException;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.util.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteBoardCommentUseCase {

    private final BoardCommentGetService boardCommentGetService;
    private final BoardCommentDeleteService boardCommentDeleteService;
    private final BaseBoardService baseBoardService;

    /**
     * 게시글 댓글 삭제
     */

    public void deleteBoardComment(String socialId, Long teamId, Long boardId, Long boardCommentId){
        // 1. 게시글 댓글 조회
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        BoardComment boardComment=boardCommentGetService.getBoardComment(boardCommentId);
        // 2. 게시글 댓글 작성자만
        if (data.getTeamMember() == boardComment.getTeamMember()) {
            // 3. 삭제
            boardCommentDeleteService.deleteBoardComment(boardComment);
            // 4. 댓글 개수 줄이기
            data.getBoard().decrComNum();
        } else throw new NotAuthByBoardCommentException();
    }
}
