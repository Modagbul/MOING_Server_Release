package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.event.FcmEvent;
import com.moing.backend.global.response.BaseServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.global.config.fcm.constant.NewNoticeUploadMessage.NEW_NOTICE_UPLOAD_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class SendBoardAlarmUseCase {

    private final TeamMemberGetService teamMemberGetService;
    private final ApplicationEventPublisher eventPublisher;

    public void sendNewUploadAlarm(BaseServiceResponse baseServiceResponse, Board board) {
        Member member = baseServiceResponse.getMember();
        Team team = baseServiceResponse.getTeam();

        if (board.isNotice() && member.isNewUploadPush()) {
            String title = NEW_NOTICE_UPLOAD_MESSAGE.title(team.getName());
            String body = NEW_NOTICE_UPLOAD_MESSAGE.body(board.getTitle());
            Optional<List<String>> fcmTokens = teamMemberGetService.getFcmTokensExceptMe(team.getTeamId(), member.getMemberId());
            if (fcmTokens.isPresent() && !fcmTokens.get().isEmpty()) {
                eventPublisher.publishEvent(new FcmEvent(title, body, fcmTokens.get()));
            }
        }
    }
}

