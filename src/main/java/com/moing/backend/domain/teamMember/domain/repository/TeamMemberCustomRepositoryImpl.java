package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.team.domain.entity.QTeam;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
                .where(teamMember.team.teamId.eq(teamId))
                .where(teamMember.team.isDeleted.eq(false))
                .fetch();
    }

    @Override
    public Optional<List<String>> findFcmTokensByTeamIdAndMemberId(Long teamId, Long memberId) {
        return Optional.ofNullable(queryFactory.select(teamMember.member.fcmToken)
                .from(teamMember)
                .where(teamMember.team.teamId.eq(teamId)) //해당 소모임에 참여하고 있고
                .where(teamMember.member.isNewUploadPush.eq(true)) //알림 설정 on해 있고
                .where(teamMember.member.memberId.ne(memberId)) //지금 유저가 아닌 경우
                .fetch());
    }

    @Override
    public Optional<List<String>> findFcmTokensByTeamId(Long teamId) {
        return Optional.ofNullable(queryFactory.select(teamMember.member.fcmToken)
                .from(teamMember)
                .where(teamMember.team.teamId.eq(teamId)) //해당 소모임에 참여하고 있고
                .where(teamMember.member.isNewUploadPush.eq(true)) //알림 설정 on해 있고
                .fetch());
    }
}
