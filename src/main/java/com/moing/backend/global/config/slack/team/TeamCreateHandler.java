package com.moing.backend.global.config.slack.team;

import com.moing.backend.global.config.slack.team.dto.TeamCreateEvent;
import com.moing.backend.global.config.slack.util.WebhookUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TeamCreateHandler {

    private final WebhookUtil webhookUtil;

    @Async
    @EventListener
    public void onTeamCreateEvent(TeamCreateEvent event) {
        webhookUtil.sendSlackTeamCreatedMessage(event.getTeamName(), event.getLeaderId());
    }
}
