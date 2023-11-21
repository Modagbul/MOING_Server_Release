package com.moing.backend.global.config.slack.team;

import com.moing.backend.global.config.slack.team.dto.TeamCreateEvent;
import com.moing.backend.global.config.slack.util.WebhookUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class TeamCreateHandler {

    private final WebhookUtil webhookUtil;

    @Async("asyncTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTeamCreateEvent(TeamCreateEvent event) {
        webhookUtil.sendSlackTeamCreatedMessage(event.getTeamName(), event.getLeaderId());
    }
}
