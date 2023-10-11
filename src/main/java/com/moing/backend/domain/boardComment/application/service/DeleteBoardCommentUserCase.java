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
public class DeleteBoardCommentUserCase {

    private final BoardCommentGetService boardCommentGetService;
    private final BoardCommentDeleteService boardCommentDeleteService;
    private final BaseBoardService baseBoardService;

    public void deleteBoardComment(String socialId, Long teamId, Long boardId, Long boardCommentId){
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        BoardComment boardComment=boardCommentGetService.getBoardComment(boardCommentId);
        if (data.getTeamMember() == boardComment.getTeamMember()) {
            boardCommentDeleteService.deleteBoardComment(boardComment);
            data.getBoard().decrComNum();
        } else throw new NotAuthByBoardCommentException();
    }
}
