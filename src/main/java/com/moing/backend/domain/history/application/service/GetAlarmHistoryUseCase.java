package com.moing.backend.domain.history.application.service;

import com.moing.backend.domain.history.application.dto.response.GetAlarmCountResponse;
import com.moing.backend.domain.history.application.dto.response.GetAlarmHistoryResponse;
import com.moing.backend.domain.history.domain.service.AlarmHistoryGetService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAlarmHistoryUseCase {

    private final MemberGetService memberGetService;
    private final AlarmHistoryGetService alarmHistoryGetService;

    /**
     * 알림 히스토리 조회
     */
    @Transactional(readOnly = true)
    public List<GetAlarmHistoryResponse> getAllAlarmHistories(String socialId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        return alarmHistoryGetService.getAlarmHistories(member.getMemberId());
    }

    /**
     * 안 읽은 알림 개수 조회
     */
    @Transactional(readOnly = true)
    public GetAlarmCountResponse getUnreadAlarmCount(String socialId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        return new GetAlarmCountResponse(alarmHistoryGetService.getUnreadAlarmCount(member.getMemberId()));
    }
}
