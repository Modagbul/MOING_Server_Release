package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.team.domain.entity.QTeam;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

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
}
