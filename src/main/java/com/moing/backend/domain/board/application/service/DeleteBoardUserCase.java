package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.domain.service.BoardDeleteService;
import com.moing.backend.domain.board.exception.NotAuthByBoardException;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.util.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteBoardUserCase {

    private final BaseBoardService baseBoardService;
    private final BoardDeleteService boardDeleteService;

    /**
     * 게시글 삭제
     */
    public void deleteBoard(String socialId, Long teamId, Long boardId){
        BaseBoardServiceResponse data= baseBoardService.getCommonData(socialId,teamId,boardId);
        if (data.getTeamMember() == data.getBoard().getTeamMember()) {
            boardDeleteService.deleteBoard(data.getBoard());
        } else throw new NotAuthByBoardException();
    }
}
