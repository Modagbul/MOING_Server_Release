package com.moing.backend.domain.board.domain.repository;

import com.moing.backend.domain.block.domain.entity.QBlock;
import com.moing.backend.domain.board.application.dto.response.BoardBlocks;
import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.QBoardBlocks;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.moing.backend.domain.block.domain.entity.QBlock.block;
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

        BooleanExpression isReadExpression = JPAExpressions
                .select(boardRead.board.boardId)
                .from(boardRead)
                .where(boardRead.member.memberId.eq(memberId),
                        boardRead.board.team.teamId.eq(teamId),
                        boardRead.board.boardId.eq(board.boardId))
                .exists();

        BooleanExpression blockCondition = JPAExpressions
                .select(block.id)
                .from(block)
                .where(block.blockMemberId.eq(memberId),
                        block.targetId.eq(board.teamMember.member.memberId))
                .notExists();

        List<BoardBlocks> allBoardBlocks = queryFactory
                .select(new QBoardBlocks(
                        board.boardId,
                        board.teamMember.member.nickName.coalesce("알수없음"),
                        board.isLeader,
                        board.teamMember.member.profileImage,
                        board.title,
                        board.content,
                        board.commentNum,
                        isReadExpression.as("isRead"),
                        board.teamMember.isDeleted,
                        board.isNotice,
                        board.teamMember.member.memberId))
                .from(board)
                .leftJoin(board.teamMember, teamMember)
                .leftJoin(board.teamMember.member, member)
                .where(board.team.teamId.eq(teamId)
                        .and(blockCondition))
                .orderBy(board.createdDate.desc())
                .fetch();

        // 공지와 일반 게시글로 나누기 및 읽음 여부 적용
        List<BoardBlocks> noticeBlocks = new ArrayList<>();
        List<BoardBlocks> regularBlocks = new ArrayList<>();
        allBoardBlocks.forEach(block -> {
            if (block.isNotice()) {
                noticeBlocks.add(block);
            } else {
                regularBlocks.add(block);
            }
        });

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
