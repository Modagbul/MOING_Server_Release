package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


import java.util.List;

import static com.moing.backend.domain.missionState.domain.entity.QMissionState.missionState;

public class MissionStateCustomRepositoryImpl implements MissionStateCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionStateCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public int getCountsByMissionId(Long missionId) {
        return queryFactory
                .select(missionState)
                .from(missionState)
                .where(
                        missionState.mission.id.eq(missionId))
                .fetch().size();
    }

    @Override
    public List<MissionState> findByMissionId(List<Long> missionId) {

        return queryFactory
                .select(missionState)
                .from(missionState)
                .where(
                        missionState.mission.id.in(missionId)
                ).fetch();

    }


}
