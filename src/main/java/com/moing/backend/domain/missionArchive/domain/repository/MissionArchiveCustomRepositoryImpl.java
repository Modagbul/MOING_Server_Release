package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.*;
import static javax.swing.Spring.constant;

public class MissionArchiveCustomRepositoryImpl implements MissionArchiveCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionArchiveCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<MissionArchive>> findSingleMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status,
                                                                              MissionArchiveStatus archiveStatus, OrderCondition orderCondition) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCondition);
        return Optional.ofNullable(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.team.teamId.eq(teamId),
                        missionArchive.member.memberId.eq(memberId),
                        missionArchive.mission.status.eq(status),
                        missionArchive.mission.type.eq(MissionType.ONCE),
                        missionArchive.status.eq(archiveStatus)
                )
                .orderBy(orderSpecifiers)
                .fetch());

    }

    private OrderSpecifier[] createOrderSpecifier(OrderCondition orderCondition) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (orderCondition.equals(OrderCondition.DUETO)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, missionArchive.mission.dueTo));
        } else if (orderCondition.equals(OrderCondition.CREATED)) {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, missionArchive.createdDate));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }


//    @Query("select m from MissionArchive as m where m.mission.id = :missionId and m.member.memberId =:memberId")
//    Optional<List<MissionArchive>> findArchivesByMissionIdAndMemberId(@Param("memberId") Long memberId, @Param("missionId")Long missionId);

    @Override
    public Optional<List<MissionArchive>> findOthersArchives(Long memberId, Long missionId) {

        return Optional.ofNullable(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.member.memberId.ne(memberId),
                        missionArchive.status.eq(MissionArchiveStatus.COMPLETE).or(missionArchive.status.eq(MissionArchiveStatus.SKIP))
                )
                .fetch()
        );
    }

    @Override
    public Optional<List<MissionArchive>> findAllMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus missionStatus) {

        return Optional.ofNullable(queryFactory
                .selectFrom(missionArchive)
                .where(
                        missionArchive.mission.status.eq(missionStatus),
                        missionArchive.mission.team.teamId.eq(teamId)
                )
                .orderBy(missionArchive.mission.dueTo.desc())
                .fetch()
        );
    }

    @Override
    public Optional<Long> findDonePeopleByMissionId(Long missionId) {
        return Optional.ofNullable(queryFactory
                .select(missionArchive.count())
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId)
                )
                .fetchFirst()

        );
    }

    @Override
    public Optional<Long> findMyDoneCountByMissionId(Long missionId, Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(missionArchive.count())
                .from(missionArchive)
                .where(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.member.memberId.eq(memberId)
                )
                .fetchFirst()
        );
    }


//    @Override
//    public Optional<List<RepeatMissionBoardRes>> findRepeatMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status) {
//        return Optional.ofNullable(queryFactory
//                .select(Projections.constructor(RepeatMissionBoardRes.class,
//                                missionArchive.mission.id,
//                                missionArchive.mission.title,
//                                missionArchive.count,
//                                missionArchive.mission.number
//                        ))
//                .from(missionArchive)
//                .where(
//                        missionArchive.mission.team.teamId.eq(teamId),
//                        missionArchive.mission.type.eq(MissionType.REPEAT),
//                        missionArchive.member.memberId.eq(memberId),
//                        missionArchive.mission.status.eq(status)
//                )
//                .groupBy(missionArchive.mission.id)
//                .fetch());
//
//
//    }
    @Override
    public Optional<List<RepeatMissionBoardRes>> findRepeatMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(RepeatMissionBoardRes.class,
                                mission.id,
                                mission.title,
                                missionArchive.count.coalesce(0L).as("num"),
                                mission.number
                        ))
                .from(mission)
                .leftJoin(mission.missionArchiveList,missionArchive)
                        .on( missionArchive.member.memberId.eq(memberId),
                        missionArchive.mission.status.eq(status))
                .where(
                        mission.team.teamId.eq(teamId),
                        mission.type.eq(MissionType.REPEAT)
//                        missionArchive.member.memberId.eq(memberId),
//                        missionArchive.mission.status.eq(status)
                )
                .groupBy(mission.id)
                .fetch());


    }
}
