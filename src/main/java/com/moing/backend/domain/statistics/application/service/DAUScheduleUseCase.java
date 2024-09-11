package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;
import com.moing.backend.global.config.slack.util.WebhookUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
@Profile("prod")
public class DAUScheduleUseCase {

    private final WebhookUtil webhookUtil;
    private final DAUManager dauManager;

    /*
    DAU 정보 : 일일 모임 생성 수, 일일 신규 가입자 수, 일일 반복 미션 생성 수, 일일 한번 미션 생성 수, 일일 미션 인증 수, 일일 불 던지기 생성
     */
    @Scheduled(cron = "0 55 23 * * *")
    public void DailyInfoAlarm() {
        Map<String, Long> todayStats = new LinkedHashMap<>();
        Map<String, Long> yesterdayStats = new LinkedHashMap<>();


        for (DAUStatusType type : DAUStatusType.values()) {

            todayStats.put(type.getMessage(), dauManager.getTodayStats(type));
            yesterdayStats.put(type.getMessage(), dauManager.getYesterdayStats(type));

        }

        webhookUtil.sendDailyStatsMessage(todayStats, yesterdayStats);
    }

}
