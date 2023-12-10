package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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

        LocalDate now = LocalDate.now();
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY; // 한 주의 시작일을 월요일로 설정
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusDays(6); // 한 주의 마지막일을 일요일로 설정

        BooleanExpression repeatTypeCondition = missionState.mission.type.eq(MissionType.REPEAT)
                .and(missionState.createdDate.goe(startOfWeek.atStartOfDay()))
                .and(missionState.createdDate.loe(endOfWeek.atStartOfDay().plusDays(1).minusNanos(1)));

        // 기본 조건
        BooleanExpression baseCondition = missionState.mission.id.eq(missionId);
        // 조건 적용
        BooleanExpression finalCondition = baseCondition.and(repeatTypeCondition);


        return queryFactory
                .select(missionState)
                .from(missionState)
                .where(finalCondition)
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
    public List<MissionState> findByMissionId(Long missionId) {

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
