package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.block.domain.repository.BlockRepositoryUtils;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.team.application.dto.response.QTeamMemberInfo;
import com.moing.backend.domain.team.application.dto.response.TeamMemberInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.team.domain.entity.QTeam.team;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

public class TeamMemberCustomRepositoryImpl implements TeamMemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    public TeamMemberCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<NewUploadInfo>> findNewUploadInfo(Long teamId, Long memberId) {
        BooleanExpression blockCondition= BlockRepositoryUtils.blockCondition(teamMember.member.memberId, memberId);

        List<NewUploadInfo> result = queryFactory.select(Projections.constructor(NewUploadInfo.class,
                        teamMember.member.fcmToken,
                        teamMember.member.memberId,
                        teamMember.member.isNewUploadPush,
                        teamMember.member.isSignOut))
                .from(teamMember)
                .where(teamMember.team.teamId.eq(teamId)
                        .and(teamMember.member.memberId.ne(memberId))
                        .and(teamMember.isDeleted.eq(false)
                                .and(blockCondition)))
                .fetch();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    @Override
    public List<TeamMemberInfo> findTeamMemberInfoByTeamId(Long teamId){
        return queryFactory
                .select(new QTeamMemberInfo(
                        teamMember.member.memberId,
                        teamMember.member.nickName,
                        teamMember.member.profileImage,
                        teamMember.member.introduction,
                        teamMember.team.leaderId))
                .from(teamMember)
                .innerJoin(teamMember.team, team) // innerJoin을 사용하여 최적화
                .where(teamMember.team.teamId.eq(teamId) // where 절을 하나로 합침
                        .and(teamMember.isDeleted.eq(false)))
                .groupBy(teamMember.member.memberId)
                .fetch();
    }

}
