package com.moing.backend.domain.boardComment.domain.repository;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long>, BoardCommentCustomRepository {
    Optional<BoardComment> findBoardCommentByBoardCommentId(Long boardCommentId);
}
