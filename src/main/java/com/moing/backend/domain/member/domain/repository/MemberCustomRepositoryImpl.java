package com.moing.backend.domain.member.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.moing.backend.domain.member.domain.entity.QMember.member;

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
                .fetchFirst() != null;
    }
}
