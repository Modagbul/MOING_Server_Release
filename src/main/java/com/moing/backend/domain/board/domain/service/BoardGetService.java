package com.moing.backend.domain.board.domain.service;

import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.GetBoardDetailResponse;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.repository.BoardRepository;
import com.moing.backend.domain.board.exception.NotFoundByBoardIdException;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class BoardGetService {

    private final BoardRepository boardRepository;

    public Board getBoard(Long boardId){
        return boardRepository.findBoardByBoardId(boardId).orElseThrow(NotFoundByBoardIdException::new);
    }

    public GetAllBoardResponse getBoardAll(Long teamId, Long memberId){
        return boardRepository.findBoardAll(teamId, memberId);
    }
}
