package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


import static com.moing.backend.domain.missionState.domain.entity.QMissionState.missionState;

public class MissionStateCustomRepositoryImpl implements MissionStateCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionStateCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Long getCountsByMissionId(Long missionId) {
        return queryFactory
                .select(missionState.count())
                .from(missionState)
                .where(
                        missionState.mission.id.eq(missionId))
                .where(
                        (missionState.status.eq(MissionArchiveStatus.COMPLETE)
                                .or( missionState.status.eq(MissionArchiveStatus.SKIP)))
                )
                .fetchFirst();
    }










}
