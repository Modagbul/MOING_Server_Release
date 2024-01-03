package com.moing.backend.domain.history.application.service;

import com.moing.backend.domain.history.domain.service.AlarmHistoryDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Profile("prod")
public class CleanupUseCase {

    private final AlarmHistoryDeleteService alarmHistoryDeleteService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupOldAlarmHistories() {
        CompletableFuture.runAsync(() -> {
            LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
            alarmHistoryDeleteService.deleteByCreatedDateBefore(oneWeekAgo);
        });
    }
}
