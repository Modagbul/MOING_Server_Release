package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentGetService;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.util.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetBoardCommentUserCase {

    private final BoardCommentGetService boardCommentGetService;
    private final BaseBoardService baseBoardService;

    public GetBoardCommentResponse getBoardCommentAll(String socialId, Long teamId, Long boardId){
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        return boardCommentGetService.getBoardCommentAll(boardId, data.getTeamMember());
    }
}
