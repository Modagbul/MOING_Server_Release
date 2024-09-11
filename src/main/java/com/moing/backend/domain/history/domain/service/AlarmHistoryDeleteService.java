package com.moing.backend.domain.history.domain.service;

import com.moing.backend.domain.history.domain.repository.AlarmHistoryRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@DomainService
@Transactional
@RequiredArgsConstructor
public class AlarmHistoryDeleteService {

    private final AlarmHistoryRepository alarmHistoryRepository;

    public void deleteByCreatedDateBefore(LocalDateTime oneWeekAgo) {
        alarmHistoryRepository.deleteByCreatedDateBefore(oneWeekAgo);
    }
}
