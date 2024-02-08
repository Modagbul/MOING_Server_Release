package com.moing.backend.domain.boardComment.domain.repository;

import com.moing.backend.domain.block.domain.repository.BlockRepositoryUtils;
import com.moing.backend.domain.boardComment.application.dto.response.CommentBlocks;
import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.dto.response.QCommentBlocks;
import com.moing.backend.domain.teamMember.domain.entity.QTeamMember;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.moing.backend.domain.boardComment.domain.entity.QBoardComment.boardComment;
import static com.moing.backend.domain.member.domain.entity.QMember.member;

public class BoardCommentCustomRepositoryImpl implements BoardCommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardCommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public GetBoardCommentResponse findBoardCommentAll(Long boardId, TeamMember teamMember) {

        BooleanExpression blockCondition = BlockRepositoryUtils.blockCondition(teamMember.getTeamMemberId(), boardComment.teamMember.member.memberId);

        List<CommentBlocks> commentBlocks = queryFactory
                .select(new QCommentBlocks(
                        boardComment.boardCommentId,
                        boardComment.content,
                        boardComment.teamMember.member.nickName,
                        boardComment.isLeader,
                        boardComment.teamMember.member.profileImage,
                        ExpressionUtils.as(JPAExpressions
                                .selectOne()
                                .from(QTeamMember.teamMember)
                                .where(QTeamMember.teamMember.eq(teamMember)
                                        .and(QTeamMember.teamMember.eq(boardComment.teamMember)))
                                .exists(), "isWriter"),
                        boardComment.teamMember.isDeleted,
                        boardComment.createdDate,
                        boardComment.teamMember.member.memberId))
                .from(boardComment)
                .leftJoin(boardComment.teamMember, QTeamMember.teamMember)
                .leftJoin(boardComment.teamMember.member, member)
                .where(boardComment.board.boardId.eq(boardId)
                        .and(blockCondition))
                .orderBy(boardComment.createdDate.asc())
                .fetch();

        return new GetBoardCommentResponse(commentBlocks);
    }
}
