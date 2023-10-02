package com.moing.backend.domain.boardComment.domain.repository;

import com.moing.backend.domain.boardComment.application.dto.response.CommentBlocks;
import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.teamMember.domain.entity.QTeamMember;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.moing.backend.domain.boardComment.domain.entity.QBoardComment.boardComment;

public class BoardCommentCustomRepositoryImpl implements BoardCommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardCommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public GetBoardCommentResponse findBoardCommentAll(Long boardId, TeamMember teamMember) {
        List<CommentBlocks> commentBlocks = queryFactory
                .select(Projections.constructor(CommentBlocks.class,
                        boardComment.boardCommentId,
                        boardComment.content,
                        boardComment.board.writerNickName,
                        boardComment.board.isLeader,
                        boardComment.board.writerProfileImage,
                        ExpressionUtils.as(JPAExpressions
                                .selectOne()
                                .from(QTeamMember.teamMember)
                                .where(QTeamMember.teamMember.eq(teamMember)
                                        .and(QTeamMember.teamMember.eq(boardComment.teamMember)))
                                .exists(), "isWriter"),
                        boardComment.teamMember.isDeleted))
                .from(boardComment)
                .where(boardComment.board.boardId.eq(boardId))
                .orderBy(boardComment.createdDate.asc())
                .fetch();

        commentBlocks.forEach(CommentBlocks::deleteMember);

        return new GetBoardCommentResponse(commentBlocks);
    }
}
