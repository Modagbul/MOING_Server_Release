package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;


import javax.persistence.EntityManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

public class MissionCustomRepositoryImpl implements MissionCustomRepository{

    private final JPAQueryFactory queryFactory;
    public MissionCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Long findMissionsCountByTeam(Long teamId) {
        return queryFactory
                .select(mission.count())
                .from(mission)
                .where(
                        mission.team.teamId.eq(teamId)
                )
                .fetchFirst();
    }

    @Override
    public Optional<List<GatherRepeatMissionRes>> findRepeatMissionByMemberId(Long memberId,List<Long>teams) {


        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();
        BooleanExpression hasAlreadyVerifiedToday = hasAlreadyVerifiedToday();

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(GatherRepeatMissionRes.class,
                        mission.id,
                        mission.team.teamId,
                        mission.team.name,
                        mission.title,
                        mission.number.stringValue(),
                        missionArchive.count().stringValue(), //고쳐야함 -> 내가 지금까지 한
                        missionArchive.status.coalesce(mission.status).stringValue(),

                        JPAExpressions
                                .select(missionArchive.member.count().stringValue())
                                .from(missionArchive)
                                .where(
                                        missionArchive.mission.id.eq(mission.id),
                                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange).and(hasAlreadyVerifiedToday)
                                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                                ),

                        mission.team.numOfMember.stringValue()

                ))
                .from(mission)
                        .leftJoin(missionArchive)
                        .on(missionArchive.mission.eq(mission),
                                missionArchive.member.memberId.eq(memberId),
//                                hasAlreadyVerifiedToday,
                                dateInRange
                        )
                .where(
                        mission.team.teamId.in(teams),
                        mission.status.eq(MissionStatus.ONGOING),
                        mission.type.eq(MissionType.REPEAT)

                )
                .groupBy(mission)
//                .groupBy(mission.id,mission.number)
//                .having(missionArchive.count().lt(mission.number)
//                ) // HAVING 절을 사용하여 조건 적용
                .orderBy(mission.createdDate.desc())
                .fetch());
    }


    @Override
    public Optional<List<Mission>> findMissionByDueTo() {

        LocalDateTime now = LocalDateTime.now();

        return Optional.ofNullable(queryFactory
                .selectFrom(mission)
                .where(
                        mission.dueTo.before(now),
                        mission.status.eq(MissionStatus.WAIT).or(mission.status.eq(MissionStatus.ONGOING)),
                        mission.type.eq(MissionType.ONCE)
                ).fetch());
    }

    @Override
    public Optional<List<Long>> findOngoingRepeatMissions() {
        return Optional.ofNullable(queryFactory
                .select(mission.id)
                .from(mission)
                .where(mission.status.eq(MissionStatus.ONGOING),
                        mission.type.eq(MissionType.REPEAT))
                .fetch());
    }

    @Override
    public Optional<List<Member>> findRepeatMissionPeopleByStatus(MissionStatus missionStatus) {

        return Optional.ofNullable(queryFactory
                .select(teamMember.member).distinct()
                .from(teamMember)
                .join(mission)
                .on(teamMember.team.eq(mission.team),
                        teamMember.member.isDeleted.ne(true),
                        teamMember.team.isDeleted.ne(true))
                .where(
                        mission.status.eq(missionStatus),
                        mission.type.eq(MissionType.REPEAT)
                ).fetch());


    }
    @Override
    public Optional<List<Mission>> findRepeatMissionByStatus(MissionStatus missionStatus) {
        return Optional.ofNullable(queryFactory
                .select(mission)
                .from(mission)
                .where(mission.status.eq(missionStatus),
                        mission.type.eq(MissionType.REPEAT))
                .fetch());
    }


    @Override
    public Optional<List<GatherSingleMissionRes>> findSingleMissionByMemberId(Long memberId, List<Long> teams) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(GatherSingleMissionRes.class,
                        mission.id,
                        mission.team.teamId,
                        mission.team.name,
                        mission.title,
                        mission.dueTo.stringValue(),
                        missionArchive.status.coalesce(mission.status).stringValue(),

                        JPAExpressions
                                .select(missionArchive.member.count().longValue())
                                .from(missionArchive)
                                .where(
                                        missionArchive.mission.id.eq(mission.id),
                                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)
                                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                                ),

                        mission.team.numOfMember.longValue()
                ))
                .from(mission)
                .leftJoin(missionArchive)
                .on(
                        mission.eq(missionArchive.mission),
                        missionArchive.member.memberId.eq(memberId)
                )
                .where(
                        mission.team.teamId.in(teams),
                        mission.status.eq(MissionStatus.ONGOING).or(mission.status.eq(MissionStatus.WAIT)),
                        mission.type.eq(MissionType.ONCE)
                )
                .orderBy(mission.dueTo.asc())
                .fetch());
    }

    public boolean findRepeatMissionsByTeamId(Long teamId) {
        return queryFactory
                .select(mission)
                .from(mission)
                .where(
                        mission.team.teamId.eq(teamId),
                        mission.type.eq(MissionType.REPEAT),
                        mission.status.eq(MissionStatus.ONGOING)
                ).fetchCount() > 2;
    }

    @Override
    public Optional<MissionReadRes> findByIds(Long memberId, Long missionId) {


        BooleanExpression isLeader = mission.team.leaderId.eq(memberId);

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(MissionReadRes.class,
                        mission.title,
                        mission.dueTo.stringValue(),
                        mission.rule,
                        mission.content,
                        mission.type.stringValue(),
                        mission.way.stringValue(),
                        isLeader))
                .from(mission)
                .where(mission.id.eq(missionId))
                .fetchOne()
        );
    }


    private BooleanExpression createRepeatTypeConditionByArchive() {
        LocalDate now = LocalDate.now();
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        // MissionType.REPEAT 인 경우의 추가적인 날짜 범위 조건
//        BooleanExpression isRepeatType = missionArchive.mission.type.eq(MissionType.REPEAT);
        BooleanExpression dateInRange = missionArchive.createdDate.goe(startOfWeek.atStartOfDay())
                .and(missionArchive.createdDate.loe(endOfWeek.atStartOfDay().plusDays(1).minusNanos(1)));

        // 조건이 MissionType.REPEAT 인 경우에만 날짜 범위 조건 적용
        return dateInRange.and(dateInRange);
    }

    private BooleanExpression createRepeatTypeConditionByState() {
        LocalDate now = LocalDate.now();
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        // MissionType.REPEAT 인 경우의 추가적인 날짜 범위 조건
//        BooleanExpression isRepeatType = missionArchive.mission.type.eq(MissionType.REPEAT);
        BooleanExpression dateInRange = missionArchive.createdDate.goe(startOfWeek.atStartOfDay())
                .and(missionArchive.createdDate.loe(endOfWeek.atStartOfDay().plusDays(1).minusNanos(1)));

        // 조건이 MissionType.REPEAT 인 경우에만 날짜 범위 조건 적용
        return dateInRange.and(dateInRange);
    }

    private BooleanExpression hasAlreadyVerifiedToday() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfToday = today.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfToday = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return missionArchive.createdDate.between(startOfToday, endOfToday);
    }



}
