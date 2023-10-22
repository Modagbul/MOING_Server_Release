package com.moing.backend.domain.teamScore.domain.repository;

import com.moing.backend.domain.teamScore.domain.entity.TeamScore;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class TeamScoreCustomRepositoryImpl implements TeamScoreCustomRepository {


    private final JPAQueryFactory queryFactory;

    public TeamScoreCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

//    public TeamScore findTeamScoreByTeamId(Long teamId) {
//        return queryFactory
//                .selectFrom(TeamSc)
//    }


}
