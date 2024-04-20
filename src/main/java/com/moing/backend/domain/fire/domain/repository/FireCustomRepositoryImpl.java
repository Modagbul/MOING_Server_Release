package com.moing.backend.domain.fire.domain.repository;

import com.moing.backend.domain.block.domain.repository.BlockRepositoryUtils;
import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.fire.domain.entity.QFire.fire;
import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;
import static com.querydsl.jpa.JPAExpressions.select;

public class FireCustomRepositoryImpl implements FireCustomRepository {
    private final JPAQueryFactory queryFactory;

    public FireCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public boolean hasFireCreatedWithinOneHour(Long throwMemberId, Long receiveMemberId) {


        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1); // 현재 시간에서 1시간을 뺀 시간
        long count = queryFactory
                .select()
                .from(fire)
                .where(
                        fire.throwMemberId.eq(throwMemberId),
                        fire.receiveMemberId.eq(receiveMemberId),
                        fire.createdDate.after(oneHourAgo) // createdDate가 oneHourAgo 이후인 데이터
                )
                .fetchCount();

        return count <= 0; // 1시간 이내에 생성된 데이터가 존재하면 true를 반환, 그렇지 않으면 false 반환
    }

    public Optional<List<FireReceiveRes>> getFireReceivers(Long teamId, Long missionId, Long memberId) {

        JPQLQuery<Long> todayCompletedMemberOfRepeat = todayRepeatMissionDone(missionId);

        JPQLQuery<Long> completedMembersOfAll = allMissionDone(missionId);

        BooleanExpression blockCondition= BlockRepositoryUtils.blockCondition(memberId, teamMember.member.memberId);

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(FireReceiveRes.class,
                        teamMember.member.memberId,
                        teamMember.member.nickName,
                        teamMember.member.profileImage
                ))
                .from(teamMember)
                .where(
                        teamMember.team.teamId.eq(teamId),
                        teamMember.member.memberId.ne(memberId),
                        teamMember.member.memberId.notIn(completedMembersOfAll)
                                .and(teamMember.member.memberId.notIn(todayCompletedMemberOfRepeat)),
                        teamMember.isDeleted.ne(Boolean.TRUE),
                        blockCondition
                )
                .fetch());

    }

    @Override
    public Long getTodayFires() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long todayFires = queryFactory
                .selectFrom(fire)
                .where(fire.createdDate.between(startOfToday, endOfToday))
                .fetchCount();

        return todayFires;
    }

    @Override
    public Long getYesterdayFires(){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long yesterdayFires = queryFactory
                .selectFrom(fire)
                .where(fire.createdDate.between(startOfYesterday, startOfToday))
                .fetchCount();

        return yesterdayFires;
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

    private JPQLQuery<Long> todayRepeatMissionDone(Long missionId) {
        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();
        BooleanExpression hasAlreadyVerifiedToday = hasAlreadyVerifiedToday();

        return
                select(missionArchive.member.memberId)
                        .from(missionArchive, mission)
                        .where(missionArchive.mission.id.eq(missionId),
                                mission.id.eq(missionId),
                                (missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange).and(hasAlreadyVerifiedToday)).or(missionArchive.mission.type.eq(MissionType.ONCE))
                        )
                        .groupBy(missionArchive.member.memberId,
                                missionArchive.mission.id,
                                missionArchive.count,
                                mission.number,
                                missionArchive.mission.type,
                                missionArchive.createdDate)
                        .having(
                                missionArchive.mission.id.eq(missionId),
                                (missionArchive.mission.type.eq(MissionType.REPEAT)).or(missionArchive.mission.type.eq(MissionType.ONCE))
                        );
    }

    private JPQLQuery<Long> allMissionDone(Long missionId) {

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        return
                select(missionArchive.member.memberId)
                        .from(missionArchive, mission)
                        .where(missionArchive.mission.id.eq(missionId),
                                mission.id.eq(missionId),
                                (missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)).or(missionArchive.mission.type.eq(MissionType.ONCE))
                        )
                        .groupBy(missionArchive.member.memberId,
                                missionArchive.mission.id,
                                missionArchive.count,
                                mission.number)
                        .having(
                                missionArchive.mission.id.eq(missionId),
                                missionArchive.count.max().goe(mission.number)
                        );
    }


}
