package com.moing.backend.domain.boardRead.domain.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.boardRead.domain.repository.BoardReadRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional
public class BoardReadSaveService {

    private final BoardReadRepository boardReadRepository;

    public void saveBoardRead(Board board, BoardRead boardRead) {
        List<BoardRead> existingBoardReads = boardReadRepository.findBoardReadByBoardAndMemberAndTeam(board, boardRead.getMember(), boardRead.getTeam());

        if (existingBoardReads.size() > 1) {
            // 첫 번째 엔티티를 제외하고 나머지 삭제
            List<BoardRead> duplicates = existingBoardReads.subList(1, existingBoardReads.size());
            boardReadRepository.deleteAll(duplicates);
        } else if (existingBoardReads.isEmpty()) {
            boardRead.updateBoard(board);
            boardReadRepository.save(boardRead);
        }
    }
}
