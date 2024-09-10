package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.block.domain.repository.BlockRepositoryUtils;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchivePhotoRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MyArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionRead.domain.repository.MissionReadRepositoryUtils;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;
//import static com.moing.backend.domain.missionState.domain.entity.QMissionState.missionState;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

@Slf4j
public class MissionArchiveCustomRepositoryImpl implements MissionArchiveCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionArchiveCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<SingleMissionBoardRes>> findSingleMissionInComplete(Long memberId, Long teamId, MissionStatus status,
                                                                             OrderCondition orderCondition) {

        BooleanExpression isReadExpression = MissionReadRepositoryUtils.isMissionReadByMemberIdAndTeamId(memberId, teamId);

        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCondition);
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(SingleMissionBoardRes.class,
                        mission.id,
                        mission.dueTo.stringValue(),
                        mission.title,
                        mission.status.stringValue(),
                        mission.type.stringValue(),
                        isReadExpression.as("isRead")
                ))
                .from(mission)
                .where(mission.notIn
                                (JPAExpressions
                                        .select(missionArchive.mission)
                                        .from(missionArchive)
                                        .where(missionArchive.member.memberId.eq(memberId),
                                                missionArchive.mission.team.teamId.eq(teamId),
                                                missionArchive.mission.type.eq(MissionType.ONCE),
                                                missionArchive.mission.status.eq(MissionStatus.SUCCESS)
                                                        .or(missionArchive.mission.status.eq(MissionStatus.ONGOING))
                                        )),
                        mission.type.eq(MissionType.ONCE),
                        mission.status.eq(MissionStatus.ONGOING).or(mission.status.eq(MissionStatus.WAIT)),
                        mission.team.teamId.eq(teamId))
                .orderBy(orderSpecifiers).fetch());



    }
    @Override
    public Optional<List<SingleMissionBoardRes>> findSingleMissionComplete(Long memberId, Long teamId, MissionStatus status,
                                                                           OrderCondition orderCondition) {
        BooleanExpression isReadExpression = MissionReadRepositoryUtils.isMissionReadByMemberIdAndTeamId(memberId, teamId);

        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCondition);
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(SingleMissionBoardRes.class,
                        mission.id,
                        mission.dueTo.stringValue(),
                        mission.title,
                        missionArchive.status.stringValue(),
                        mission.type.stringValue(),
                        isReadExpression.as("isRead")
                ))
                .from(mission)
                .join(mission.missionArchiveList,missionArchive)
                .on(missionArchive.member.memberId.eq(memberId))
                .where(
                        mission.team.teamId.eq(teamId),
                        mission.type.eq(MissionType.ONCE),
                        mission.status.eq(MissionStatus.ONGOING)
                )
                .orderBy(orderSpecifiers).fetch());


    }

    private OrderSpecifier[] createOrderSpecifier(OrderCondition orderCondition) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (orderCondition.equals(OrderCondition.DUETO)) {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, mission.dueTo));
        } else if (orderCondition.equals(OrderCondition.CREATED)) {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, missionArchive.createdDate));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    @Override
    public Optional<List<MissionArchive>> findOneMyArchives(Long memberId,Long missionId,Long count) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return Optional.ofNullable(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.member.memberId.eq(memberId),
                        missionArchive.count.eq(count),

                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)
                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                )
                .orderBy(missionArchive.createdDate.desc())
                .fetch()

        );
    }

    @Override
    public Optional<List<MissionArchive>> findMyArchives(Long memberId,Long missionId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return Optional.ofNullable(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.member.memberId.eq(memberId),
                        (missionArchive.mission.type.eq(MissionType.REPEAT)
                                .and(missionArchive.mission.status.eq(MissionStatus.ONGOING)).and(dateInRange))
                                .or(missionArchive.mission.type.eq(MissionType.REPEAT)
                                        .and(missionArchive.mission.status.eq(MissionStatus.END)))
                                .or(missionArchive.mission.type.eq(MissionType.ONCE))

                )
                .orderBy(missionArchive.createdDate.desc())
                .fetch()

        );
    }


    @Override
    public Optional<List<MissionArchive>> findOthersArchives(Long memberId, Long missionId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        BooleanExpression blockCondition= BlockRepositoryUtils.blockCondition(memberId, missionArchive.member.memberId);

        return Optional.ofNullable(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.member.memberId.ne(memberId),
                        missionArchive.status.eq(MissionArchiveStatus.COMPLETE).or(missionArchive.status.eq(MissionArchiveStatus.SKIP)),
                        blockCondition,
                        (missionArchive.mission.type.eq(MissionType.REPEAT)
                                .and(missionArchive.mission.status.eq(MissionStatus.ONGOING)).and(dateInRange))
                                .or(missionArchive.mission.type.eq(MissionType.REPEAT)
                                        .and(missionArchive.mission.status.eq(MissionStatus.END)))
                                .or(missionArchive.mission.type.eq(MissionType.ONCE))

                )
                .orderBy(missionArchive.createdDate.desc())
                .fetch()
        );
    }


    @Override
    public Optional<Long> findDonePeopleBySingleMissionId(Long missionId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return Optional.of(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),

                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)
                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                )
                .groupBy(missionArchive.member)
                .fetchCount()

        );
    }
    @Override
    public Optional<Long> findDonePeopleByRepeatMissionId(Long missionId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return Optional.of(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),

                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)
                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                )
                .groupBy(missionArchive.member,missionArchive.mission.number)
                .having(missionArchive.count().goe(missionArchive.mission.number))
                .fetchCount()

        );
    }

    @Override
    public Optional<Long> findMyDoneCountByMissionId(Long missionId, Long memberId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();
        return Optional.ofNullable(queryFactory
                .select(missionArchive.count())
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.member.memberId.eq(memberId),
                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)
                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                )
                .fetchFirst()
        );
    }


    @Override
    public Optional<List<RepeatMissionBoardRes>> findRepeatMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status) {

        BooleanExpression isReadExpression = MissionReadRepositoryUtils.isMissionReadByMemberIdAndTeamId(memberId, teamId);


        BooleanExpression dateInRange = createRepeatTypeConditionByState();
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(RepeatMissionBoardRes.class,
                        mission.id,
                        mission.title,
//                                missionState.count().coalesce(0L).as("done"),
                        missionArchive.count(),
                        mission.number,
                        mission.way.stringValue(),
                        mission.status.stringValue(),
                        isReadExpression.as("isRead")
                ))
                .from(mission)
                .leftJoin(missionArchive)
                .on(missionArchive.mission.eq(mission),
                        missionArchive.member.memberId.eq(memberId),
                        dateInRange
                )
                .where(
                        mission.team.teamId.eq(teamId),
                        mission.type.eq(MissionType.REPEAT),
                        mission.status.eq(MissionStatus.ONGOING).or(mission.status.eq(MissionStatus.WAIT))
                )
                .groupBy(mission.id,mission.number)
//                .having(missionState.count().lt(mission.number)) // HAVING 절을 사용하여 조건 적용
                .orderBy(missionArchive.count().desc())
                .fetch());

    }

    @Override
    public Optional<List<FinishMissionBoardRes>> findFinishMissionsByStatus(Long memberId, Long teamId) {

        Expression<String> dueToString = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %H:%i.%s')", mission.dueTo);
        Expression<String> status = Expressions.stringTemplate(String.valueOf(missionArchive.status));
        Expression<String> type = Expressions.stringTemplate(String.valueOf(mission.type));


        return Optional.ofNullable(queryFactory
                .selectDistinct(Projections.constructor(FinishMissionBoardRes.class,
                        mission.id,
                        mission.dueTo.stringValue(),
                        mission.title,
                        missionArchive.status.stringValue().coalesce("FAIL").as("status"),
                        mission.type.stringValue(),
                        mission.way.stringValue()
                ))
                .from(mission)
                .leftJoin(mission.missionArchiveList,missionArchive)
                .on(missionArchive.member.memberId.eq(memberId))
                .where(
                        mission.team.teamId.eq(teamId),
                        mission.status.eq(MissionStatus.SUCCESS).or(mission.status.eq(MissionStatus.END))
                ).orderBy(mission.lastModifiedDate.desc())
                .fetch()
        );
    }


    public Optional<List<MissionArchivePhotoRes>> findTop5ArchivesByTeam(List<Long> teamIds) {
        List<Tuple> queryResults = queryFactory
                .select(missionArchive.mission.team.teamId, missionArchive.archive)
                .from(missionArchive)
                .where(missionArchive.mission.team.teamId.in(teamIds),
                        missionArchive.mission.way.eq(MissionWay.PHOTO),
                        missionArchive.status.eq(MissionArchiveStatus.COMPLETE),
                        missionArchive.archive.ne("https://modagbul.s3.ap-northeast-2.amazonaws.com/reportImage.png"),
                        missionArchive.archive.ne("https://mo-ing.s3.ap-northeast-2.amazonaws.com/reportImage.png"))
                .orderBy(missionArchive.createdDate.desc())
                .limit(14)
                .fetch();

        List<MissionArchivePhotoRes> resultDTOs = new ArrayList<>();

        for (Tuple tuple : queryResults) {
            Long teamId = tuple.get(missionArchive.mission.team.teamId);
            String photo = tuple.get(missionArchive.archive);

            // Check if a TeamPhotoDTO with the same teamId already exists in the list
            MissionArchivePhotoRes existingDTO = resultDTOs.stream()
                    .filter(dto -> dto.getTeamId().equals(teamId))
                    .findFirst()
                    .orElse(null);

            if (existingDTO != null) {
                existingDTO.getPhoto().add(photo);
            } else {
                List<String> photoList = new ArrayList<>();
                photoList.add(photo);
                resultDTOs.add(new MissionArchivePhotoRes(teamId, photoList));
            }
        }

        return Optional.ofNullable(resultDTOs);
    }


    public Boolean findMyArchivesToday(Long memberId, Long missionId) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfToday = today.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfToday = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        long count = queryFactory
                .selectFrom(missionArchive)
                .where(
                        missionArchive.member.memberId.eq(memberId),
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.createdDate.between(startOfToday, endOfToday), // createdDate와 오늘의 시작과 끝을 비교,

                        missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)
                                .or(missionArchive.mission.type.eq(MissionType.ONCE))
                )
                .fetchCount();

        return count > 0;
    }


    private BooleanExpression createRepeatTypeConditionByArchive() {
        LocalDate now = LocalDate.now();
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

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

        BooleanExpression dateInRange = missionArchive.createdDate.goe(startOfWeek.atStartOfDay())
                .and(missionArchive.createdDate.loe(endOfWeek.atStartOfDay().plusDays(1).minusNanos(1)));

        // 조건이 MissionType.REPEAT 인 경우에만 날짜 범위 조건 적용
        return dateInRange.and(dateInRange);
    }

//
//    @Query(value = "SELECT distinct COALESCE(tmSub.fcm_token,'undef') as fcmToken, tmSub.member_id as memberId " +
//            "FROM (SELECT distinct COALESCE(tm.member_id, 0) AS member_id, t.team_id, me.fcm_token " +
//            "FROM mission m " +
//            "LEFT JOIN team t ON m.team_id = t.team_id " +
//            "LEFT JOIN team_member tm ON t.team_id = tm.team_id AND tm.is_deleted = 'False' " +
//            "LEFT JOIN member me on tm.member_id = me.member_id) tmSub " +
//            "LEFT JOIN mission m ON NOT (m.status = 'END' OR m.status = 'SUCCESS') and m.team_id = tmSub.team_id " +
//            "LEFT JOIN mission_archive ms ON m.mission_id = ms.mission_id and ms.member_id = tmSub.member_id " +
//            "GROUP BY tmSub.member_id, m.mission_id, m.number " +
//            "HAVING COUNT(ms.mission_archive_id) < m.number", nativeQuery = true
//    )

    @Override
    public Optional<List<Member>> findHavingRemainMissionsByQuerydsl() {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return Optional.ofNullable(queryFactory
                .select(teamMember.member).distinct()
                .from(teamMember)
                .join(mission)
                .on(teamMember.team.eq(mission.team),
                        teamMember.team.isDeleted.ne(true),
                        ((mission.status.eq(MissionStatus.ONGOING).or(mission.status.eq(MissionStatus.WAIT)))
                                .and(mission.type.eq(MissionType.ONCE)))
                                .or(mission.status.eq(MissionStatus.ONGOING).and(mission.type.eq(MissionType.REPEAT)))
                )
                .leftJoin(missionArchive)
                .on(missionArchive.mission.eq(mission),
                        missionArchive.member.eq(teamMember.member),
                        ((mission.type.eq(MissionType.REPEAT).and(dateInRange))
                                .or(mission.type.eq(MissionType.ONCE)))
                )
                .groupBy(teamMember.member,mission,mission.number)
                        .having(missionArchive.count().lt(mission.number),
                                teamMember.member.isDeleted.ne(true))
                .fetch());


    }

    @Override
    public MyArchiveStatus findMissionStatusById(Long memberId, Long missionId, Long teamId) {

        return queryFactory
                .select(Projections.constructor(MyArchiveStatus.class,
                        mission.status.eq(MissionStatus.END),
                        new CaseBuilder()
                                .when(mission.status.eq(MissionStatus.WAIT).or(mission.status.eq(MissionStatus.ONGOING)))
                                .then(missionArchive.status.stringValue().coalesce(mission.status.stringValue()))
                                .otherwise(missionArchive.status.stringValue().coalesce("FAIL"))

                )).distinct()
                .from(mission)
                .leftJoin(missionArchive)
                .on(
                        missionArchive.mission.eq(mission),
                        missionArchive.member.memberId.eq(memberId))
                .where(
                        mission.team.teamId.eq(teamId),
                        mission.id.eq(missionId)
                )
                .fetchFirst();

    }

    public Long getCountsByMissionId(Long missionId) {

        BooleanExpression repeatTypeCondition = createRepeatTypeConditionByArchive();
        // 기본 조건
        BooleanExpression baseCondition = missionArchive.mission.id.eq(missionId);
        // 조건 적용
        BooleanExpression finalCondition = baseCondition.and(repeatTypeCondition);

        return (long) queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(finalCondition)
                .fetch().size();
    }

    @Override
    public Long getTodayMissionArchives() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long todayMissionArchives = queryFactory
                .selectFrom(missionArchive)
                .where(missionArchive.createdDate.between(startOfToday, endOfToday))
                .fetchCount();

        return todayMissionArchives;
    }

    @Override
    public Long getYesterdayMissionArchives() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long yesterdayMissionArchives = queryFactory
                .selectFrom(missionArchive)
                .where(missionArchive.createdDate.between(startOfYesterday, startOfToday))
                .fetchCount();

        return yesterdayMissionArchives;
    }


}