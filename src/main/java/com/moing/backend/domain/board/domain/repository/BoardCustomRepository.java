package com.moing.backend.domain.board.domain.repository;

import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;

public interface BoardCustomRepository {
    GetAllBoardResponse findBoardAll(Long teamId, Long memberId);
    Integer findUnReadBoardNum(Long teamId, Long memberId);
}
