package com.moing.backend.domain.board.domain.repository;

import com.moing.backend.domain.board.application.dto.response.BoardBlocks;
import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.domain.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.moing.backend.domain.board.domain.entity.QBoard.board;
import static com.moing.backend.domain.boardRead.domain.entity.QBoardRead.boardRead;

public class    BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public GetAllBoardResponse findBoardAll(Long teamId, Long memberId) {
        // 전체 게시글 보기
        List<Board> allBoards = queryFactory.selectFrom(board)
                .where(board.team.teamId.eq(teamId))
                .orderBy(board.createdDate.desc())
                .fetch();

        // 읽은 게시글 보기
        List<Board> readBoards = queryFactory.selectFrom(board)
                .join(boardRead).on(boardRead.board.eq(board))
                .where(board.team.teamId.eq(teamId))
                .where(boardRead.member.memberId.eq(memberId))
                .groupBy(board.boardId)
                .orderBy(board.createdDate.desc())
                .fetch();

        // 공지와 일반 게시글로 나누기
        List<Board> notices = allBoards.stream().filter(Board::isNotice).collect(Collectors.toList());
        List<Board> regularPosts = allBoards.stream().filter(b -> !b.isNotice()).collect(Collectors.toList());

        // Board -> BoardBlocks 변환
        Function<Board, BoardBlocks> toBoardBlocks = b -> {
            boolean isRead = readBoards.contains(b);
            BoardBlocks boardBlocks = new BoardBlocks(
                    b.getBoardId(),
                    b.getWriterNickName(),
                    b.isLeader(),
                    b.getWriterProfileImage(),
                    b.getTitle(),
                    b.getContent(),
                    b.getCommentNum(),
                    b.getTeamMember().isDeleted()
            );
            if (isRead) {
                boardBlocks.readBoard();
            }
            return boardBlocks;
        };

        List<BoardBlocks> noticeBlocks = notices.stream().map(toBoardBlocks).collect(Collectors.toList());
        List<BoardBlocks> regularBlocks = regularPosts.stream().map(toBoardBlocks).collect(Collectors.toList());

        return new GetAllBoardResponse(noticeBlocks.size(), noticeBlocks, regularBlocks.size(), regularBlocks);
    }
}
