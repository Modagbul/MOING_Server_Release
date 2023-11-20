package com.moing.backend.global.util;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Service
@Slf4j
public class SlackService {

    @Value("${webhook.slack.error_url}")
    private String errorWebhookUrl;

    @Value("${webhook.slack.team_alarm_url}")
    private String teamAlarmWebhookUrl;

    private final Slack slackClient = Slack.getInstance();

    // 공통 슬랙 메시지 전송 메서드
    @Async
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

    // 슬랙 에러 알림 메서드
    public void sendSlackAlertErrorLog(Exception e, HttpServletRequest request) {
        String message = "[500 에러가 발생했습니다.]";
        List<Attachment> attachments = List.of(generateSlackErrorAttachment(e, request));
        sendSlackMessage(errorWebhookUrl, message, attachments);
    }

    // 슬랙 소모임 생성 알림 메서드
    public void sendSlackTeamCreatedMessage(String teamName, Long leaderId) {
        String message = String.format("새로운 소모임 '%s'이(가) 생성되었습니다!", teamName);
        List<Attachment> attachments = List.of(generateSlackTeamAttachment(teamName, leaderId));
        sendSlackMessage(teamAlarmWebhookUrl, message, attachments);
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

    // attachment 생성 메서드
    private Attachment generateSlackErrorAttachment(Exception e, HttpServletRequest request) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        String xffHeader = request.getHeader("X-FORWARDED-FOR");
        return Attachment.builder()
                .color("ff0000")
                .title(requestTime + " 발생 에러 로그")
                .fields(List.of(
                                generateSlackField("Request IP", xffHeader == null ? request.getRemoteAddr() : xffHeader),
                                generateSlackField("Request URL", request.getRequestURL() + " " + request.getMethod()),
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
