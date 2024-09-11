package com.moing.backend.domain.boardRead.domain.repository;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface BoardReadRepository extends JpaRepository<BoardRead, Long> {

    List<BoardRead> findBoardReadByBoardAndMemberAndTeam(Board board, Member member, Team team);
}
