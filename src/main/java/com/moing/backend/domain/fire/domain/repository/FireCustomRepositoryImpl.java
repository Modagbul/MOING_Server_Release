package com.moing.backend.domain.fire.domain.repository;

import com.moing.backend.domain.fire.domain.entity.Fire;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

public class FireCustomRepositoryImpl implements FireCustomRepository {
    private final JPAQueryFactory queryFactory;

    public FireCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//
//    Optional<Fire> findMyRecentFire(Long memberId){
//        return Optional.ofNullable(queryFactory
//                .select()
//                .from()
//                .where()
//                .fetchFirst()
//        );
//    }

}
