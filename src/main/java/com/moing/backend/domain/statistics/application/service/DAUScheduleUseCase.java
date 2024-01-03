package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.statistics.application.dto.DailyStats;
import com.moing.backend.global.config.slack.util.WebhookUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class DAUScheduleUseCase {

    private final WebhookUtil webhookUtil;
    private final MemberGetService memberGetService;

    public static final String DAILY_TEAM_CREATION_COUNT = "[DAU] 일일 모임 생성 수";
    public static final String DAILY_NEW_MEMBER_COUNT = "[DAU] 일일 신규 가입자 수";
    public static final String DAILY_REPEAT_MISSION_COUNT = "[DAU] 일일 반복 미션 생성 개수";
    public static final String DAILY_ONCE_MISSION_COUNT = "[DAU] 일일 한번 미션 생성 개수";

    /*
    DAU 정보 : 일일 모임 생성 수, 일일 신규 가입자 수, 일일 반복 미션 생성 수, 일일 한번 미션 생성 수
     */
    @Scheduled(cron = "0 59 23 * * *")
    public void DailyTeamCreationInfoAlarm() {
        Map<String, Long> todayStats = new LinkedHashMap<>();
        Map<String, Long> yesterdayStats = new LinkedHashMap<>();

        DailyStats dailyStats = memberGetService.getDailyStats();
        todayStats.put(DAILY_TEAM_CREATION_COUNT, dailyStats.getTodayNewTeams());
        yesterdayStats.put(DAILY_TEAM_CREATION_COUNT, dailyStats.getYesterdayNewTeams());

        todayStats.put(DAILY_NEW_MEMBER_COUNT, dailyStats.getTodayNewMembers());
        yesterdayStats.put(DAILY_NEW_MEMBER_COUNT, dailyStats.getYesterdayNewMembers());

        todayStats.put(DAILY_REPEAT_MISSION_COUNT, dailyStats.getTodayRepeatMission());
        yesterdayStats.put(DAILY_REPEAT_MISSION_COUNT, dailyStats.getYesterdayRepeatMission());

        todayStats.put(DAILY_ONCE_MISSION_COUNT, dailyStats.getTodayOnceMission());
        yesterdayStats.put(DAILY_ONCE_MISSION_COUNT, dailyStats.getYesterdayRepeatMission());
        webhookUtil.sendDailyStatsMessage(todayStats, yesterdayStats);
    }

}
