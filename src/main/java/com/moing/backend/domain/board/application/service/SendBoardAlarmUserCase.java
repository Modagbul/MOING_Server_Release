package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import com.moing.backend.global.response.BaseServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.global.config.fcm.constant.NewUploadTitle.UPLOAD_NOTICE_NEW_TITLE;

@Service
@RequiredArgsConstructor
@Transactional
public class SendBoardAlarmUserCase {

    private final TeamMemberGetService teamMemberGetService;
    private final FcmService fcmService;

    public void sendNewUploadAlarm(BaseServiceResponse baseServiceResponse, Board board) {
        Member member = baseServiceResponse.getMember();
        Team team = baseServiceResponse.getTeam();

        if (board.isNotice() && member.isNewUploadPush()) {
            String title = team.getName() + " " + UPLOAD_NOTICE_NEW_TITLE.getTitle();
            String message = board.getTitle();
            Optional<List<String>> fcmTokens = teamMemberGetService.getFcmTokensExceptMe(team.getTeamId(), member.getMemberId());
            if (fcmTokens.isPresent() && !fcmTokens.get().isEmpty()) {
                MultiRequest toMultiRequest = new MultiRequest(fcmTokens.get(), title, message);
                fcmService.sendMultipleDevices(toMultiRequest);
            }
        }
    }
}

