package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<List<MissionState>> findFinishMission() {
        return Optional.ofNullable(queryFactory
                .select(missionState)
                .from(missionState)
                .where(
                        missionState.mission.dueTo.ne(LocalDateTime.now()),
                        missionState.mission.status.eq(MissionStatus.ONGOING)

                ).fetch()
        );
    }

    public Optional<MissionState> findMissionStateByMemberAndMission(Member member, Mission mission) {
        return Optional.ofNullable(queryFactory
                .selectFrom(missionState)
                .where(missionState.mission.eq(mission),
                        missionState.member.eq(member))
                .fetchFirst());
    }

}
