package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.global.config.fcm.constant.NewUploadTitle.UPLOAD_NOTICE_NEW_TITLE;

@Service
@Transactional
@RequiredArgsConstructor
public class SendTeamAlarmUserCase {
    private final TeamMemberGetService teamMemberGetService;
    private final FcmService fcmService;

    public void sendApprovalAlarm(Long teamId) {
        //TODO: 승인, 반려 문구 constant로 
        String title = "소모임 승인 안내";
        String message = "소모임이 승인되었습니다.";
        sendAlarm(teamId, title, message);
    }

    public void sendRejectionAlarm(Long teamId) {
        String title = "소모임 반려 안내";
        String message = "소모임이 반려되었습니다.";
        sendAlarm(teamId, title, message);
    }

    private void sendAlarm(Long teamId, String title, String message) {
        Optional<List<String>> fcmTokens = teamMemberGetService.getFcmTokens(teamId);
        if(fcmTokens.isPresent() && !fcmTokens.get().isEmpty()) {
            MultiRequest toMultiRequest = new MultiRequest(fcmTokens.get(), title, message);
            fcmService.sendMultipleDevices(toMultiRequest);
        }
    }
}

