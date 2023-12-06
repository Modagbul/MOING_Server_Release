package com.moing.backend.domain.history.domain.service;

import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import com.moing.backend.domain.history.domain.repository.AlarmHistoryRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class AlarmHistorySaveService {

    private final AlarmHistoryRepository alarmHistoryRepository;

    public void saveAlarmHistories(List<AlarmHistory> alarmHistories){
        alarmHistoryRepository.saveAll(alarmHistories);
    }

    public void saveAlarmHistory(AlarmHistory alarmHistory){
        alarmHistoryRepository.save(alarmHistory);
    }

}
