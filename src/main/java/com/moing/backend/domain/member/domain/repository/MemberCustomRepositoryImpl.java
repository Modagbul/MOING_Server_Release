package com.moing.backend.domain.member.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.statistics.application.dto.DailyStats;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.moing.backend.domain.member.domain.entity.QMember.member;
import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.team.domain.entity.QTeam.team;

public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MemberCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean checkNickname(String nickname) {
        return queryFactory
                .selectOne()
                .from(member)
                .where(member.nickName.eq(nickname))
                .where(member.isDeleted.eq(false))
                .fetchFirst() != null;
    }

    @Override
    public Optional<Member> findNotDeletedBySocialId(String socialId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.socialId.eq(socialId))
                .where(member.isDeleted.eq(false))
                .fetchOne());
    }

    @Override
    public Optional<Member> findNotDeletedByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.email.eq(email))
                .where(member.isDeleted.eq(false))
                .fetchOne());
    }

    @Override
    public Optional<Member> findNotDeletedByMemberId(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.memberId.eq(id))
                .where(member.isDeleted.eq(false))
                .fetchOne());
    }

    @Override
    public DailyStats getDailyStats() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);

        long todayNewMembers = queryFactory
                .selectFrom(member)
                .where(member.createdDate.between(startOfToday, endOfToday))
                .fetchCount();

        long yesterdayNewMembers = queryFactory
                .selectFrom(member)
                .where(member.createdDate.between(startOfYesterday, startOfToday))
                .fetchCount();

        long todayNewTeams = queryFactory
                .selectFrom(team)
                .where(team.createdDate.between(startOfToday, endOfToday))
                .fetchCount();

        long yesterdayNewTeams = queryFactory
                .selectFrom(team)
                .where(team.createdDate.between(startOfYesterday, startOfToday))
                .fetchCount();

        long todayRepeatMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfToday, endOfToday)
                        .and(mission.type.eq(MissionType.REPEAT)))
                .fetchCount();

        long yesterdayRepeatMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfYesterday, startOfToday)
                        .and(mission.type.eq(MissionType.REPEAT)))
                .fetchCount();

        long todayOnceMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfToday, endOfToday)
                        .and(mission.type.eq(MissionType.ONCE)))
                .fetchCount();

        long yesterdayOnceMissions = queryFactory
                .select(mission)
                .from(mission)
                .where(mission.createdDate.between(startOfYesterday, startOfToday)
                        .and(mission.type.eq(MissionType.ONCE)))
                .fetchCount();

        return new DailyStats(
                todayNewMembers,
                yesterdayNewMembers,
                todayNewTeams,
                yesterdayNewTeams,
                todayRepeatMissions,
                yesterdayRepeatMissions,
                todayOnceMissions,
                yesterdayOnceMissions);
    }

}
