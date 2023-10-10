package com.moing.backend.domain.fire.domain.repository;

import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.entity.QFire;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.fire.domain.entity.QFire.fire;

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

        return count > 0; // 1시간 이내에 생성된 데이터가 존재하면 true를 반환, 그렇지 않으면 false 반환
    }


//    public List<Member> get

}
