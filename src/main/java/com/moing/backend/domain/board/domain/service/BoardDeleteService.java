package com.moing.backend.domain.board.domain.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.repository.BoardRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class BoardDeleteService {

    private final BoardRepository boardRepository;

    public void deleteBoard(Board board){
        boardRepository.delete(board);
    }
}
