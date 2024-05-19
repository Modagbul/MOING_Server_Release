package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.QMission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;
import static com.querydsl.jpa.JPAExpressions.select;

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

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(GatherRepeatMissionRes.class,
                        mission.id,
                        mission.team.teamId,
                        mission.team.name,
                        mission.title,
                        mission.number.stringValue(),
                        missionArchive.count.max().coalesce(0L).stringValue(),
                        missionArchive.status.coalesce(mission.status).stringValue(),

                        JPAExpressions
                                .select(teamMember.member.countDistinct().stringValue())
                                .from(teamMember)
                                .where(
                                        teamMember.team.eq(mission.team),
                                        teamMember.member.memberId.in(RepeatMissionDonePeopleByWeek(mission.id))
                                        .or(teamMember.member.memberId.in(RepeatMissionDonePeopleByDay(mission.id))),
                                        teamMember.isDeleted.ne(Boolean.TRUE)
                                ),

                        mission.team.numOfMember.stringValue()

                ))
                .from(mission)
                        .leftJoin(missionArchive)
                        .on(missionArchive.mission.eq(mission),
                                missionArchive.member.memberId.eq(memberId),
                                dateInRange
                        )
                .where(
                        mission.team.teamId.in(teams),
                        mission.status.eq(MissionStatus.ONGOING),
                        mission.type.eq(MissionType.REPEAT)

                )
                .groupBy(mission)
                .orderBy(mission.createdDate.desc())
                .fetch());
    }

    private JPQLQuery<Long> RepeatMissionDonePeopleByDay(NumberPath<Long> missionId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();
        BooleanExpression hasAlreadyVerifiedToday = hasAlreadyVerifiedToday();

        return
                select(missionArchive.member.memberId)
                        .from(missionArchive)
                        .where(
                                missionArchive.mission.id.eq(missionId),
                                (missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange).and(hasAlreadyVerifiedToday))
                        ).distinct();
    }

    private JPQLQuery<Long> RepeatMissionDonePeopleByWeek(NumberPath<Long> missionId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return select(missionArchive.member.memberId)
                                .from(missionArchive)
                                .where(
                                        missionArchive.mission.id.eq(missionId),
                                        (missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange))
                                )
                                .groupBy(missionArchive.mission.number, missionArchive.count)
                                .having(
                                        missionArchive.count.max().goe(missionArchive.mission.number))
                .distinct();

    }


    @Override
    public Optional<List<Mission>> findMissionByDueTo() {

        return Optional.ofNullable(queryFactory
                .selectFrom(mission)
                .where(
                        mission.status.eq(MissionStatus.WAIT).or(mission.status.eq(MissionStatus.ONGOING)),
                        mission.dueTo.before(LocalDateTime.now()),
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
                                .select(missionArchive.member.count().stringValue())
                                .from(missionArchive)
                                .where(
                                        missionArchive.mission.id.eq(mission.id),
                                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)
                                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                                ),

                        mission.team.numOfMember.stringValue()
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
                .orderBy(missionArchive.status.asc(),mission.dueTo.asc(),missionArchive.createdDate.desc())
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

        Mission mission = queryFactory
                .selectFrom(QMission.mission)
                .where(QMission.mission.id.eq(missionId))
                .fetchOne();

        if (mission == null) {
            return Optional.empty();
        }

        boolean isLeader = mission.getMakerId().equals(memberId) || mission.getTeam().getLeaderId().equals(memberId);

        MissionReadRes result = new MissionReadRes(
                mission.getTitle(),
                mission.getDueTo().toString(),
                mission.getRule(),
                mission.getContent(),
                mission.getType().toString(),
                mission.getWay().toString(),
                isLeader
        );

        return Optional.of(result);
    }

    @Override
    public Long getTodayOnceMissions() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);


        long todayOnceMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfToday, endOfToday)
                        .and(mission.type.eq(MissionType.ONCE)))
                .fetchCount();

        return todayOnceMissions;

    }

    @Override
    public Long getYesterdayOnceMissions() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long yesterdayOnceMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfYesterday, startOfToday)
                        .and(mission.type.eq(MissionType.ONCE)))
                .fetchCount();

        return yesterdayOnceMissions;
    }

    @Override
    public Long getTodayRepeatMissions() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long todayRepeatMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfToday, endOfToday)
                        .and(mission.type.eq(MissionType.REPEAT)))
                .fetchCount();

        return todayRepeatMissions;
    }

    @Override
    public Long getYesterdayRepeatMissions() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long yesterdayRepeatMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfYesterday, startOfToday)
                        .and(mission.type.eq(MissionType.REPEAT)))
                .fetchCount();


        return yesterdayRepeatMissions;
    }




    private BooleanExpression createRepeatTypeConditionByArchive() {
        LocalDate now = LocalDate.now();
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return missionArchive.createdDate.goe(startOfWeek.atStartOfDay())
                .and(missionArchive.createdDate.loe(endOfWeek.atStartOfDay().plusDays(1).minusNanos(1)));

    }

    private BooleanExpression hasAlreadyVerifiedToday() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfToday = today.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfToday = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return missionArchive.createdDate.between(startOfToday, endOfToday);
    }



}
