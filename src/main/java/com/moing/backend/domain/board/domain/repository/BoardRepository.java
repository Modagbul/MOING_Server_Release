package com.moing.backend.domain.board.domain.repository;

import com.moing.backend.domain.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

    Optional<Board> findBoardByBoardId(Long boardId);
}
