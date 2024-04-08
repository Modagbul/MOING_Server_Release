package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;

public class MissionArchiveStateCustomRepositoryImpl implements MissionArchiveStateCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionArchiveStateCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public int getCountsByMissionId(Long missionId) {

        LocalDate now = LocalDate.now();
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY; // 한 주의 시작일을 월요일로 설정
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusDays(6); // 한 주의 마지막일을 일요일로 설정

        BooleanExpression repeatTypeCondition = (missionArchive.mission.type.eq(MissionType.REPEAT)
                .and(missionArchive.createdDate.goe(startOfWeek.atStartOfDay()))
                .and(missionArchive.createdDate.loe(endOfWeek.atStartOfDay().plusDays(1).minusNanos(1)))).or(missionArchive.mission.type.eq(MissionType.ONCE));

        // 기본 조건
        BooleanExpression baseCondition = missionArchive.mission.id.eq(missionId);
        // 조건 적용
        BooleanExpression finalCondition = baseCondition.and(repeatTypeCondition);


        return queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(finalCondition)
                .fetch().size();
    }



}
