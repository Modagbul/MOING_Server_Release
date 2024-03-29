package com.moing.backend.domain.board.domain.repository;

import com.moing.backend.domain.block.domain.repository.BlockRepositoryUtils;
import com.moing.backend.domain.board.application.dto.response.BoardBlocks;
import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.QBoardBlocks;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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


        BooleanExpression blockCondition = BlockRepositoryUtils.blockCondition(memberId, board.teamMember.member.memberId);

        List<BoardBlocks> allBoardBlocks = queryFactory
                .select(new QBoardBlocks(
                        board.boardId,
                        board.teamMember.member.nickName.coalesce("알수없음"),
                        board.isLeader,
                        board.teamMember.member.profileImage,
                        board.title,
                        board.content,
                        board.commentNum,
                        isReadExpression(memberId, teamId).as("isRead"),
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
        BooleanExpression isNotReadExpression = isReadExpression(memberId, teamId).not();

        BooleanExpression blockCondition = BlockRepositoryUtils.blockCondition(memberId, board.teamMember.member.memberId);

        Long unReadBoardsCount = queryFactory
                .select(board.count())
                .from(board)
                .leftJoin(boardRead)
                .on(board.boardId.eq(boardRead.board.boardId)
                        .and(boardRead.member.memberId.eq(memberId)))
                .where(board.team.teamId.eq(teamId)
                        .and(isNotReadExpression)
                        .and(blockCondition))
                .fetchOne();

        return Math.toIntExact(unReadBoardsCount != null ? unReadBoardsCount : 0);
    }


    private BooleanExpression isReadExpression(Long memberId, Long teamId) {
        return JPAExpressions
                .select(boardRead.board.boardId)
                .from(boardRead)
                .where(boardRead.member.memberId.eq(memberId),
                        boardRead.board.team.teamId.eq(teamId),
                        boardRead.board.boardId.eq(board.boardId))
                .exists();
    }
}
