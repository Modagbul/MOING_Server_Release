package com.moing.backend.domain.board.domain.repository;

import com.moing.backend.domain.board.application.dto.response.BoardBlocks;
import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.QBoardBlocks;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.moing.backend.domain.board.domain.entity.QBoard.board;
import static com.moing.backend.domain.boardRead.domain.entity.QBoardRead.boardRead;
import static com.moing.backend.domain.member.domain.entity.QMember.member;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory queryFactory;
    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public GetAllBoardResponse findBoardAll(Long teamId, Long memberId) {

        // 전체 게시글 보기
        List<BoardBlocks> allBoardBlocks = queryFactory
                .select(new QBoardBlocks(
                        board.boardId,
                        board.teamMember.member.nickName,
                        board.isLeader,
                        board.teamMember.member.profileImage,
                        board.title,
                        board.content,
                        board.commentNum,
                        board.teamMember.isDeleted,
                        board.isNotice,
                        board.teamMember.member.memberId
                ))
                .from(board)
                .leftJoin(board.teamMember, teamMember)
                .leftJoin(board.teamMember.member, member)
                .where(board.team.teamId.eq(teamId))
                .orderBy(board.createdDate.desc())
                .fetch();

        // 읽은 게시글 목록 조회
        List<Long> readBoardIds = queryFactory
                .select(boardRead.board.boardId)
                .from(boardRead)
                .where(boardRead.member.memberId.eq(memberId))
                .where(boardRead.board.team.teamId.eq(teamId))
                .fetch();

        // 읽은 게시글 표시
        allBoardBlocks.forEach(boardBlock -> {
            if (readBoardIds.contains(boardBlock.getBoardId())) {
                boardBlock.readBoard();
            }
        });

        // 공지와 일반 게시글로 나누기
        List<BoardBlocks> noticeBlocks = allBoardBlocks.stream()
                .filter(BoardBlocks::isNotice)
                .collect(Collectors.toList());
        List<BoardBlocks> regularBlocks = allBoardBlocks.stream()
                .filter(b -> !b.isNotice())
                .collect(Collectors.toList());

        return new GetAllBoardResponse(noticeBlocks.size(), noticeBlocks, regularBlocks.size(), regularBlocks);
    }

    @Override
    public Integer findUnReadBoardNum(Long teamId, Long memberId) {
        // 전체 게시글 수
        Long allBoards = queryFactory
                .select(board.count())
                .from(board)
                .where(board.team.teamId.eq(teamId))
                .fetchFirst();

        // 멤버가 읽은 게시글 수
        Long readBoards = queryFactory
                .select(boardRead.board.boardId)
                .distinct()
                .from(boardRead)
                .where(boardRead.member.memberId.eq(memberId))
                .where(boardRead.board.team.teamId.eq(teamId))
                .stream().count();

        return Math.toIntExact(allBoards - readBoards);
    }
}
