package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.QMission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;
import static com.moing.backend.domain.missionState.domain.entity.QMissionState.missionState;
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

        BooleanExpression dateInRange = createRepeatTypeConditionByState();

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(GatherRepeatMissionRes.class,
                        mission.id,
                        mission.team.teamId,
                        mission.team.name,
                        mission.title,
                        mission.number.stringValue(),
                        missionState.count().stringValue(),
                        mission.status.stringValue()
                ))
                .from(mission)
                        .leftJoin(missionState)
                        .on(missionState.mission.eq(mission),
                                missionState.member.memberId.eq(memberId),
                                dateInRange
                        )
                .where(
                        mission.team.teamId.in(teams),
                        mission.status.eq(MissionStatus.ONGOING),
                        mission.type.eq(MissionType.REPEAT)

                )
                .groupBy(mission.id,mission.number)
                .having(missionState.count().lt(mission.number)
                        ) // HAVING 절을 사용하여 조건 적용
                .orderBy(missionState.count().desc())
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


        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(GatherSingleMissionRes.class,
                        mission.id,
                        mission.team.teamId,
                        mission.team.name,
                        mission.title,
                        mission.dueTo.stringValue(),
                        mission.status.stringValue()
                ))
                .from(mission)
                .leftJoin(missionState).on(
                        mission.eq(missionState.mission),
                        missionState.member.memberId.eq(memberId)
                        )
                .where(
                        mission.team.teamId.in(teams),
                        mission.status.eq(MissionStatus.ONGOING).or(mission.status.eq(MissionStatus.WAIT)),
                        mission.type.eq(MissionType.ONCE),
                        missionState.id.isNull()
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
        BooleanExpression dateInRange = missionState.createdDate.goe(startOfWeek.atStartOfDay())
                .and(missionState.createdDate.loe(endOfWeek.atStartOfDay().plusDays(1).minusNanos(1)));

        // 조건이 MissionType.REPEAT 인 경우에만 날짜 범위 조건 적용
        return dateInRange.and(dateInRange);
    }



}
