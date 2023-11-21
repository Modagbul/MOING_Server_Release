package com.moing.backend.global.config.slack.util;

import javax.servlet.http.HttpServletRequest;

public interface WebhookUtil {

    void sendSlackAlertErrorLog(HttpServletRequest request, Exception e);

    void sendSlackTeamCreatedMessage(String teamName, Long leaderId);
}
