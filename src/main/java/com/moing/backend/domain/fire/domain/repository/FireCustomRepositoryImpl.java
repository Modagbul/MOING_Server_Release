package com.moing.backend.domain.fire.domain.repository;

import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.fire.domain.entity.QFire.fire;
import static com.moing.backend.domain.member.domain.entity.QMember.member;
import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;
import static com.moing.backend.domain.team.domain.entity.QTeam.team;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;
import static com.querydsl.jpa.JPAExpressions.max;
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

        BooleanExpression dateInRange = createRepeatTypeConditionByArchive();

        JPQLQuery<Long> missionDonePeople = select(missionArchive.member.memberId)
                .from(missionArchive, mission)
                .where(missionArchive.mission.id.eq(missionId),
                        mission.id.eq(missionId)
                )
                .groupBy(missionArchive.member.memberId,
                        missionArchive.mission.id,
                        missionArchive.count,
                        mission.number)
                .having(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.count.max().goe(mission.number),
                        (missionArchive.mission.type.eq(MissionType.REPEAT).and(dateInRange)).or(missionArchive.mission.type.eq(MissionType.ONCE))
                );

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1); // 현재 시간에서 1시간을 뺀 시간

        BooleanExpression oneHourStatus = JPAExpressions
                .select()
                .from(fire)
                .where(
                        fire.throwMemberId.eq(memberId),
                        fire.receiveMemberId.notIn(missionDonePeople),
                        fire.createdDate.after(oneHourAgo) // createdDate가 oneHourAgo 이후인 데이터
                )

                .exists();


        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(FireReceiveRes.class,
                                teamMember.member.memberId,
                                teamMember.member.nickName,
                                teamMember.member.profileImage
//                                oneHourStatus.stringValue()
//                                teamMember.member.nickName
                                ))
                .from(teamMember)
                .where(
                        teamMember.team.teamId.eq(teamId),
                        teamMember.member.memberId.ne(memberId),
                        teamMember.member.memberId.notIn(missionDonePeople),
                        teamMember.isDeleted.ne(Boolean.TRUE)
                )
                .fetch());

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

}
