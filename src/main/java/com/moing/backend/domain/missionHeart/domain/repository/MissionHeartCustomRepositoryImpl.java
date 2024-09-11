package com.moing.backend.domain.missionHeart.domain.repository;

import com.moing.backend.domain.missionHeart.application.dto.MissionHeartRes;
import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.domain.missionHeart.domain.entity.QMissionHeart;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.moing.backend.domain.missionHeart.domain.entity.QMissionHeart.missionHeart;

public class MissionHeartCustomRepositoryImpl implements MissionHeartCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MissionHeartCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public boolean findAlreadyHeart(Long memberId, Long archiveId) {

        return queryFactory
                .selectFrom(missionHeart)
                .where(
                        missionHeart.pushMemberId.eq(memberId),
                        missionHeart.missionArchive.id.eq(archiveId)
                ).fetchCount() > 0;

    }
    public MissionHeart findByMemberIdAndArchiveId(Long memberId, Long archiveId) {

        return queryFactory
                .selectFrom(missionHeart)
                .where(
                        missionHeart.pushMemberId.eq(memberId),
                        missionHeart.missionArchive.id.eq(archiveId)
                ).fetchFirst();

    }

}
