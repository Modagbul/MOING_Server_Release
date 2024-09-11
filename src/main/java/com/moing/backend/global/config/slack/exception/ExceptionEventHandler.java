package com.moing.backend.global.config.slack.exception;

import com.moing.backend.global.config.slack.exception.dto.ExceptionEvent;
import com.moing.backend.global.config.slack.util.WebhookUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("prod")
public class ExceptionEventHandler {

    private final WebhookUtil webhookUtil;

    @Async
    @EventListener
    public void onExceptionEvent(ExceptionEvent event) {
        webhookUtil.sendSlackAlertErrorLog(event.getRequest(), event.getException());
    }
}