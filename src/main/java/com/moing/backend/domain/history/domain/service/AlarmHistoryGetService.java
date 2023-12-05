package com.moing.backend.domain.history.domain.service;

import com.moing.backend.domain.history.application.dto.response.GetAlarmHistoryResponse;
import com.moing.backend.domain.history.domain.repository.AlarmHistoryRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class AlarmHistoryGetService {

    private final AlarmHistoryRepository alarmHistoryRepository;

    public List<GetAlarmHistoryResponse> getAlarmHistories(Long memberId){
        return alarmHistoryRepository.findAlarmHistoriesByMemberId(memberId);
    }
}
