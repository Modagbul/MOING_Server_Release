package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchivePhotoRes;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import feign.Param;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.*;
import static com.moing.backend.domain.missionState.domain.entity.QMissionState.missionState;
import static javax.swing.Spring.constant;

public class MissionArchiveCustomRepositoryImpl implements MissionArchiveCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionArchiveCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<SingleMissionBoardRes>> findSingleMissionInComplete(Long memberId, Long teamId, MissionStatus status,
                                                                                    OrderCondition orderCondition) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCondition);
        String missionArchiveStatus = "INCOMPLETE";
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(SingleMissionBoardRes.class,
                        mission.id,
                        mission.dueTo.stringValue(),
                        mission.title,
                        Expressions.constant(missionArchiveStatus),
                        mission.type.stringValue()
                ))
                .from(mission)
                .where(mission.notIn
                                (JPAExpressions
                                        .select(missionState.mission)
                                        .from(missionState)
                                        .where(missionState.member.memberId.eq(memberId),
                                                missionState.mission.team.teamId.eq(teamId),
                                                missionState.mission.type.eq(MissionType.ONCE),
                                                missionState.mission.status.eq(MissionStatus.SUCCESS)
                                                        .or(missionState.mission.status.eq(MissionStatus.ONGOING))
                                        )),
                        mission.type.eq(MissionType.ONCE),
                        mission.team.teamId.eq(teamId))
                .orderBy(orderSpecifiers).fetch());


    }
    @Override
    public Optional<List<SingleMissionBoardRes>> findSingleMissionComplete(Long memberId, Long teamId, MissionStatus status,
                                                                                    OrderCondition orderCondition) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCondition);
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(SingleMissionBoardRes.class,
                        mission.id,
                        mission.dueTo.stringValue(),
                        mission.title,
                        missionArchive.status.stringValue(),
                        mission.type.stringValue()
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
    public Optional<List<MissionArchive>> findMyArchives(Long memberId,Long missionId) {

        return Optional.ofNullable(queryFactory
                .select(missionArchive)
                 .from(missionArchive)
                 .where(
                        missionArchive.mission.id.eq(missionId),
                        missionArchive.member.memberId.eq(memberId)
                )
                .orderBy(missionArchive.createdDate.desc())
                .fetch()

        );
    }


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
                .orderBy(missionArchive.createdDate.desc())
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

    @Override
    public Optional<List<FinishMissionBoardRes>> findFinishMissionsByStatus(Long memberId, Long teamId) {

        Expression<String> dueToString = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %H:%i.%s')", mission.dueTo);
        Expression<String> status = Expressions.stringTemplate(String.valueOf(missionArchive.status));
        Expression<String> type = Expressions.stringTemplate(String.valueOf(mission.type));


        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(FinishMissionBoardRes.class,
                    mission.id,
                    mission.dueTo.stringValue(),
                    mission.title,
                    missionArchive.status.stringValue(),
                    mission.type.stringValue()
                ))
                .from(mission)
                .leftJoin(mission.missionArchiveList,missionArchive)
                        .on(missionArchive.member.memberId.eq(memberId))
                .where(
                        mission.team.teamId.eq(teamId),
                        mission.status.eq(MissionStatus.SUCCESS).or(mission.status.eq(MissionStatus.FAIL))
                )
                .fetch()
        );
    }


    public Optional<List<MissionArchivePhotoRes>> findTop5ArchivesByTeam(List<Long> teamIds) {
        List<Tuple> queryResults = queryFactory
                .select(missionArchive.mission.team.teamId, missionArchive.archive)
                .from(missionArchive)
                .where(missionArchive.mission.team.teamId.in(teamIds))
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


}
