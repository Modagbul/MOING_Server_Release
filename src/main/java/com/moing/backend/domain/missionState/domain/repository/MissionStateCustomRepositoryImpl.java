package com.moing.backend.domain.missionState.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class MissionStateCustomRepositoryImpl implements MissionStateCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionStateCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

//    public MissionStatusRes getMissionStatus() {
//
//        queryFactory
//                .select()
//                .from(MissionState)
//                .where(
//
//                )
//    }



}
