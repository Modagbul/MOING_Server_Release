package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.mission.domain.entity.QMission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive;
import com.moing.backend.domain.teamMember.domain.repository.OrderCondition;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import feign.Param;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.moing.backend.domain.missionArchive.domain.entity.QMissionArchive.*;

public class MissionArchiveCustomRepositoryImpl implements MissionArchiveCustomRepository {
    private final JPAQueryFactory queryFactory;

    public MissionArchiveCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<MissionArchive>> findSingleMissionArchivesByMemberId(Long memberId, List<Long> missionIds, String status, String archiveStatus,OrderCondition orderCondition) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCondition);
        return Optional.ofNullable(queryFactory
                .select(missionArchive)
                .from(missionArchive)
                .where(
                        missionArchive.mission.status.eq(MissionStatus.ONGOING),
                        missionArchive.mission.id.in(missionIds),
                        missionArchive.mission.status.eq(MissionStatus.valueOf(status)),
                        missionArchive.status.eq(MissionArchiveStatus.valueOf(archiveStatus))
                )
                .orderBy(orderSpecifiers)
                .fetch());

    }

    private OrderSpecifier[] createOrderSpecifier(OrderCondition orderCondition) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if(orderCondition.equals(OrderCondition.DUETO)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, missionArchive.mission.dueTo));
        }else if(orderCondition.equals(OrderCondition.CREATED)){
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, missionArchive.createdDate));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }


}
