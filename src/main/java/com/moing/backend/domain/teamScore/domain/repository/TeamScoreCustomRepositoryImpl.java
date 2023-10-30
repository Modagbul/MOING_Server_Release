package com.moing.backend.domain.teamScore.domain.repository;

import static com.moing.backend.domain.teamScore.domain.entity.QTeamScore.teamScore;

import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class TeamScoreCustomRepositoryImpl implements TeamScoreCustomRepository {


    private final JPAQueryFactory queryFactory;

    public TeamScoreCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public TeamScore findTeamScoreByTeam(Long teamId) {
        return queryFactory
                .selectFrom(teamScore)
                .where(teamScore.team.teamId.eq(teamId)).fetchFirst();
    }


}
