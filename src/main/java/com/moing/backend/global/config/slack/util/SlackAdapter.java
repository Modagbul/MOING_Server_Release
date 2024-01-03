package com.moing.backend.global.config.slack.util;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.slack.api.webhook.WebhookPayloads.payload;

@RequiredArgsConstructor
@Slf4j
@Component
public class SlackAdapter implements WebhookUtil {

    @Value("${webhook.slack.error_url}")
    private String errorWebhookUrl;

    @Value("${webhook.slack.team_alarm_url}")
    private String infoWebhookUrl;

    private final Slack slackClient = Slack.getInstance();

    public void sendSlackMessage(String webhookUrl, String message, List<Attachment> attachments) {
        try {
            slackClient.send(webhookUrl, payload(p -> p
                    .text(message)
                    .attachments(attachments)
            ));
        } catch (IOException slackError) {
            log.debug("Slack 통신과의 예외 발생");
        }
    }

    @Override
    public void sendSlackAlertErrorLog(HttpServletRequest request, Exception e) {
        String message = "[500 에러가 발생했습니다.]";
        List<Attachment> attachments = List.of(generateSlackErrorAttachment(e, request));
        sendSlackMessage(errorWebhookUrl, message, attachments);
    }

    @Override
    public void sendSlackTeamCreatedMessage(String teamName, Long leaderId) {
        String message = String.format("[새로운 소모임 '%s'이(가) 생성되었습니다.]", teamName);
        List<Attachment> attachments = List.of(generateSlackTeamAttachment(teamName, leaderId));
        sendSlackMessage(infoWebhookUrl, message, attachments);
    }

    private Attachment generateSlackTeamAttachment(String teamName, Long leaderId) {
        return Attachment.builder()
                .color("36a64f")
                .title("소모임 생성 알림")
                .fields(List.of(
                        generateSlackField("소모임 이름", teamName),
                        generateSlackField("생성자 아이디", String.valueOf(leaderId))
                ))
                .build();
    }

    @Override
    public void sendDailyStatsMessage(Map<String, Long> todayStats, Map<String, Long> yesterdayStats) {
        String message = "[일일 통계 알림]";
        List<Attachment> attachments = todayStats.keySet().stream()
                .map(key -> generateDailyStatsAttachment(key, todayStats.get(key), yesterdayStats.getOrDefault(key, 0L)))
                .collect(Collectors.toList());

        sendSlackMessage(infoWebhookUrl, message, attachments);
    }

    private Attachment generateDailyStatsAttachment(String title, long todayCount, long yesterdayCount) {
        return Attachment.builder()
                .color("1A66CC") // 색상 설정
                .title(title)
                .fields(List.of(
                        generateSlackField("Today", String.valueOf(todayCount) + " 개"),
                        generateSlackField("Yesterday", String.valueOf(yesterdayCount) + " 개")
                ))
                .build();
    }

    // attachment 생성 메서드
    private Attachment generateSlackErrorAttachment(Exception e, HttpServletRequest request) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        String xffHeader = request.getHeader("X-FORWARDED-FOR");
        return Attachment.builder()
                .color("ff0000")
                .title(requestTime + " 발생 에러 로그")
                .fields(List.of(
                                generateSlackField("Request IP", xffHeader == null ? request.getRemoteAddr() : xffHeader),
                                generateSlackField("Request URL", request.getMethod() + " " + request.getRequestURL()),
                                generateSlackField("Error Message", e.getMessage())
                        )
                )
                .build();
    }

    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}