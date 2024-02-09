package com.moing.backend.domain.boardComment.domain.repository;

import com.moing.backend.domain.block.domain.repository.BlockRepositoryUtils;
import com.moing.backend.domain.boardComment.application.dto.response.CommentBlocks;
import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.dto.response.QCommentBlocks;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.teamMember.domain.entity.QTeamMember;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.boardComment.domain.entity.QBoardComment.boardComment;
import static com.moing.backend.domain.member.domain.entity.QMember.member;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

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

    @Override
    public Optional<List<NewUploadInfo>> findNewUploadInfo(Long memberId, Long boardId) {
        BooleanExpression blockCondition= BlockRepositoryUtils.blockCondition(boardComment.teamMember.member.memberId, memberId);

        List<NewUploadInfo> result = queryFactory.select(Projections.constructor(NewUploadInfo.class,
                        boardComment.teamMember.member.fcmToken,
                        boardComment.teamMember.member.memberId,
                        boardComment.teamMember.member.isNewUploadPush,
                        boardComment.teamMember.member.isSignOut))
                .distinct()
                .from(teamMember)
                .leftJoin(boardComment.teamMember, QTeamMember.teamMember)
                .leftJoin(boardComment.teamMember.member, member)
                .where(boardComment.board.boardId.eq(boardId) //게시글의 댓글인데
                        .and(boardComment.teamMember.member.memberId.ne(memberId)) //나는 포함 안하고
                        .and(boardComment.teamMember.isDeleted.eq(false)) //탈퇴한 사람도 포함 안함
                                .and(blockCondition))
                .fetch();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
