package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.global.config.fcm.constant.NewMissionTitle.NEW_SINGLE_MISSION_COMING;

@Service
@Transactional
@RequiredArgsConstructor
public class SendMissionCreateAlarmUseCase {

    private final TeamMemberGetService teamMemberGetService;
    private final ApplicationEventPublisher eventPublisher;

    public void sendNewMissionUploadAlarm(Member member, Mission mission) {
        Team team = mission.getTeam();
        String title = team.getName() + " " + NEW_SINGLE_MISSION_COMING.getTitle();
        String message = mission.getTitle();

        Optional<List<MemberIdAndToken>> memberIdAndTokensByPush = teamMemberGetService.getNewUploadPushInfo(team.getTeamId(), member.getMemberId());
        Optional<List<MemberIdAndToken>> memberIdAndTokensBySave = teamMemberGetService.getNewUploadSaveInfo(team.getTeamId(), member.getMemberId());
        // 알림 보내기
        eventPublisher.publishEvent(new MultiFcmEvent(title, message, memberIdAndTokensByPush, memberIdAndTokensBySave, createIdInfo(team.getTeamId(), mission.getId()), team.getName(), AlarmType.NEW_UPLOAD, PagePath.MISSION_PATH.getValue()));
    }

    private String createIdInfo(Long teamId, Long missionId) {
        JSONObject jo = new JSONObject();
        jo.put("isRepeated", false);
        jo.put("teamId", teamId);
        jo.put("missionId", missionId);
        return jo.toJSONString();
    }
}

