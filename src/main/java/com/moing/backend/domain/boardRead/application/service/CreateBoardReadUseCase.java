package com.moing.backend.domain.boardRead.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardRead.application.mapper.BoardReadMapper;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.boardRead.domain.service.BoardReadSaveService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateBoardReadUseCase {

    private final BoardReadMapper boardReadMapper;
    private final BoardReadSaveService boardReadSaveService;

    public void createBoardRead(Team team, Member member, Board board){
        BoardRead boardRead = boardReadMapper.toBoardRead(team, member);
        boardReadSaveService.saveBoardRead(board, boardRead);
    }
}
