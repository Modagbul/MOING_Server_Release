package com.moing.backend.domain.history.domain.repository;

import com.moing.backend.domain.history.application.dto.response.GetAlarmHistoryResponse;
import com.moing.backend.domain.history.application.dto.response.QGetAlarmHistoryResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import javax.persistence.EntityManager;
import java.util.List;

import static com.moing.backend.domain.history.domain.entity.QAlarmHistory.alarmHistory;
import static com.moing.backend.domain.team.domain.entity.QTeam.team;

public class AlarmHistoryCustomRepositoryImpl implements AlarmHistoryCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public AlarmHistoryCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public List<GetAlarmHistoryResponse> findAlarmHistoriesByMemberId(Long memberId) {
        List<GetAlarmHistoryResponse> response= queryFactory.select(new QGetAlarmHistoryResponse(alarmHistory.id,
                        alarmHistory.type,
                        alarmHistory.path,
                        alarmHistory.idInfo,
                        alarmHistory.title,
                        alarmHistory.body,
                        alarmHistory.name,
                        alarmHistory.isRead,
                        alarmHistory.createdDate))
                .from(alarmHistory)
                .where(alarmHistory.receiverId.eq(memberId))
                .orderBy(alarmHistory.createdDate.desc())
                .fetch();

        readAlarmHistory(memberId);
        return response;
    }

    private void readAlarmHistory(Long memberId){
        queryFactory
                .update(alarmHistory)
                .set(alarmHistory.isRead, true)
                .where(alarmHistory.receiverId.eq(memberId))
                        .execute();

        em.flush();
        em.clear();
    }
    @Override
    public String findUnreadAlarmCount(Long memberId) {
        Long count = queryFactory.select(alarmHistory.count())
                .from(alarmHistory)
                .where(alarmHistory.receiverId.eq(memberId)
                        .and(alarmHistory.isRead.eq(false)))
                .fetchOne();

        return count != null ? (count > 99 ? "99+" : count.toString()) : "0";
    }
}
