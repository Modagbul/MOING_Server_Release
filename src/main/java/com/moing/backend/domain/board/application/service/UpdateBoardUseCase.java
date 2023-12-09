package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.UpdateBoardResponse;
import com.moing.backend.domain.board.exception.NotAuthByBoardException;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.utils.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateBoardUseCase {

    private final BaseBoardService baseBoardService;

    /**
     * 게시글 수정
     */
    public UpdateBoardResponse updateBoard(String socialId, Long teamId, Long boardId, UpdateBoardRequest updateBoardRequest){
        // 1. 게시글 조회
        BaseBoardServiceResponse data= baseBoardService.getCommonData(socialId, teamId, boardId);
        // 2. 게시글 작성자만
        if (data.getTeamMember() == data.getBoard().getTeamMember()) {
            // 3. 수정
            data.getBoard().updateBoard(updateBoardRequest);
            return new UpdateBoardResponse(data.getBoard().getBoardId());
        } else throw new NotAuthByBoardException();
    }
}
