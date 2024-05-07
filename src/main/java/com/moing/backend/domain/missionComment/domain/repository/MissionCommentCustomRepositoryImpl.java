package com.moing.backend.domain.missionComment.domain.repository;

import com.moing.backend.domain.block.domain.repository.BlockRepositoryUtils;
import com.moing.backend.domain.comment.application.dto.response.CommentBlocks;
import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.comment.application.dto.response.QCommentBlocks;
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

import static com.moing.backend.domain.member.domain.entity.QMember.member;
import static com.moing.backend.domain.missionComment.domain.entity.QMissionComment.missionComment;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

public class MissionCommentCustomRepositoryImpl implements MissionCommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    public MissionCommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public GetCommentResponse findMissionCommentAll(Long missionArchiveId, TeamMember teamMember) {

        BooleanExpression blockCondition = BlockRepositoryUtils.blockCondition(teamMember.getTeamMemberId(), missionComment.teamMember.member.memberId);

        List<CommentBlocks> commentBlocks = queryFactory
                .select(new QCommentBlocks(
                        missionComment.missionCommentId,
                        missionComment.content,
                        missionComment.teamMember.member.nickName,
                        missionComment.isLeader,
                        missionComment.teamMember.member.profileImage,
                        ExpressionUtils.as(JPAExpressions
                                .selectOne()
                                .from(QTeamMember.teamMember)
                                .where(QTeamMember.teamMember.eq(teamMember)
                                        .and(QTeamMember.teamMember.eq(missionComment.teamMember)))
                                .exists(), "isWriter"),
                        missionComment.teamMember.isDeleted,
                        missionComment.createdDate,
                        missionComment.teamMember.member.memberId))
                .from(missionComment)
                .leftJoin(missionComment.teamMember, QTeamMember.teamMember)
                .leftJoin(missionComment.teamMember.member, member)
                .where(missionComment.missionArchive.id.eq(missionArchiveId)
                        .and(blockCondition))
                .orderBy(missionComment.createdDate.asc())
                .fetch();

        return new GetCommentResponse(commentBlocks);
    }

    @Override
    public Optional<List<NewUploadInfo>> findNewUploadInfo(Long memberId, Long missionArchiveId) {
        BooleanExpression blockCondition= BlockRepositoryUtils.blockCondition(missionComment.teamMember.member.memberId, memberId);

        List<NewUploadInfo> result = queryFactory.select(Projections.constructor(NewUploadInfo.class,
                        missionComment.teamMember.member.fcmToken,
                        missionComment.teamMember.member.memberId,
                        missionComment.teamMember.member.isNewUploadPush,
                        missionComment.teamMember.member.isSignOut))
                .distinct()
                .from(missionComment)
                .leftJoin(missionComment.teamMember, teamMember)
                .leftJoin(missionComment.teamMember.member, member)
                .where(missionComment.missionArchive.id.eq(missionArchiveId) //게시글의 댓글인데
                        .and(missionComment.teamMember.member.memberId.ne(memberId)) //나는 포함 안하고
                        .and(missionComment.teamMember.isDeleted.eq(false)) //탈퇴한 사람도 포함 안함
                        .and(blockCondition))
                .fetch();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
