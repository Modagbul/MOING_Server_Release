package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.missionArchive;

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

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(GatherRepeatMissionRes.class,
                        mission.id,
                        mission.team.name,
                        mission.title,
                        mission.number.stringValue(),
                        missionArchive.count().stringValue()

                ))
                .from(mission,missionArchive)
                .where(
                        missionArchive.mission.eq(mission),
                        missionArchive.member.memberId.eq(memberId),
                        mission.team.teamId.in(teams),
                        mission.status.eq(MissionStatus.ONGOING),
                        mission.type.eq(MissionType.REPEAT)
                )
                .groupBy(mission.id)
                .fetch());
    }

    @Override
    public Optional<List<GatherSingleMissionRes>> findSingleMissionByMemberId(Long memberId, List<Long> teams) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(GatherSingleMissionRes.class,
                        mission.id,
                        mission.team.name,
                        mission.title,
                        mission.dueTo.stringValue()
                ))
                .from(mission)
                .where(
                        mission.team.teamId.in(teams),
                        mission.status.eq(MissionStatus.ONGOING),
                        mission.type.eq(MissionType.ONCE)

                )
                .fetch()

        );
    }
}
