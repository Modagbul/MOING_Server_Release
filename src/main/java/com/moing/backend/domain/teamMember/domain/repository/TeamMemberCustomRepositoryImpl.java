package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.team.application.dto.response.QTeamMemberInfo;
import com.moing.backend.domain.team.application.dto.response.TeamMemberInfo;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.entity.QTeam;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
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
    public List<Long> findMemberIdsByTeamId(Long teamId) {
        return queryFactory
                .select(teamMember.member.memberId)
                .from(teamMember)
                .where(teamMember.team.teamId.eq(teamId)
                        .and(teamMember.team.isDeleted.eq(false)))
                .fetch();
    }

    @Override
    public Optional<List<String>> findFcmTokensByTeamIdAndMemberId(Long teamId, Long memberId) {
        List<String> result = queryFactory.select(teamMember.member.fcmToken)
                .from(teamMember)
                .where(teamMember.team.teamId.eq(teamId)
                        .and(teamMember.member.isNewUploadPush.eq(true))
                        .and(teamMember.member.memberId.ne(memberId)))
                .fetch();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    @Override
    public Optional<List<String>> findFcmTokensByTeamId(Long teamId) {
        List<String> result = queryFactory.select(teamMember.member.fcmToken)
                .from(teamMember)
                .where(teamMember.team.teamId.eq(teamId)
                        .and(teamMember.member.isNewUploadPush.eq(true)))
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

    @Override
    public List<TeamMember> findTeamMemberByMemberId(Long memberId) {
        return queryFactory.selectFrom(teamMember)
                .where(teamMember.member.memberId.eq(memberId)
                        .and(teamMember.isDeleted.eq(false)))
                .fetch();
    }
}
