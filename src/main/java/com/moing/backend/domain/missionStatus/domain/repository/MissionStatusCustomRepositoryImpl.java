package com.moing.backend.domain.missionStatus.domain.repository;

import com.moing.backend.domain.missionStatus.application.dto.MissionStatusRes;
import com.moing.backend.domain.missionStatus.domain.entity.MissionStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class MissionStatusCustomRepositoryImpl implements MissionStatusCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionStatusCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

//    public MissionStatusRes getMissionStatus() {
//
//        queryFactory
//                .select()
//                .from(MissionStatus)
//                .where(
//
//                )
//    }



}
