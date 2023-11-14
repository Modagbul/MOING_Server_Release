package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
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

import static com.moing.backend.global.config.fcm.constant.NewMissionTitle.NEW_SINGLE_MISSION_COMING;
import static com.moing.backend.global.config.fcm.constant.NewUploadTitle.UPLOAD_NOTICE_NEW_TITLE;

@Service
@Transactional
@RequiredArgsConstructor
public class SendMissionCreateAlarmUseCase {

    private final TeamMemberGetService teamMemberGetService;
    private final FcmService fcmService;

    public void sendNewMissionUploadAlarm(Member member, Mission mission) {
        Team team = mission.getTeam();
        Optional<List<String>> fcmTokensExceptMe = teamMemberGetService.getFcmTokensExceptMe(team.getTeamId(), member.getMemberId());

        String title = team.getName() + " " + NEW_SINGLE_MISSION_COMING.getTitle();
        String message = mission.getTitle();

        Optional<List<String>> fcmTokens = teamMemberGetService.getFcmTokensExceptMe(team.getTeamId(), member.getMemberId());
        if (fcmTokens.isPresent() && !fcmTokens.get().isEmpty()) {
            MultiRequest toMultiRequest = new MultiRequest(fcmTokens.get(), title, message);
            fcmService.sendMultipleDevices(toMultiRequest);

        }
    }
}

