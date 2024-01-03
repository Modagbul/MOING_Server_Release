package com.moing.backend.global.config.slack.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface WebhookUtil {

    void sendSlackAlertErrorLog(HttpServletRequest request, Exception e);

    void sendSlackTeamCreatedMessage(String teamName, Long leaderId);

    void sendDailyStatsMessage(Map<String, Long> todayStats, Map<String, Long> yesterdayStats);
}
