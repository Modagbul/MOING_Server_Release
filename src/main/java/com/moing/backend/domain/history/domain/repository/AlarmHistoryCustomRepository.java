package com.moing.backend.domain.history.domain.repository;

import com.moing.backend.domain.history.application.dto.response.GetAlarmHistoryResponse;

import java.util.List;

public interface AlarmHistoryCustomRepository {

    List<GetAlarmHistoryResponse> findAlarmHistoriesByMemberId(Long memberId);
}
