package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
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

import static com.moing.backend.domain.missionArchive.application.service.MissionArchiveCreateMessage.CREATOR_CREATE_MISSION_ARCHIVE;
import static com.moing.backend.domain.missionArchive.application.service.MissionArchiveCreateMessage.TEAM_AND_TITLE;

@Service
@Transactional
@RequiredArgsConstructor
public class SendMissionArchiveCreateAlarmUseCase {

    private final TeamMemberGetService teamMemberGetService;
    private final ApplicationEventPublisher eventPublisher;

    public void sendNewMissionArchiveUploadAlarm(Member member, Mission mission) {
        Team team = mission.getTeam();

        String title = CREATOR_CREATE_MISSION_ARCHIVE.to(member.getNickName());
        String message = TEAM_AND_TITLE.teamAndTitle(team.getName(),mission.getTitle());


        Optional<List<NewUploadInfo>> newUploadInfos=teamMemberGetService.getNewUploadInfo(team.getTeamId(), member.getMemberId());

        Optional<List<MemberIdAndToken>> memberIdAndTokensByPush = AlarmHistoryMapper.getNewUploadPushInfo(newUploadInfos);
        Optional<List<MemberIdAndToken>> memberIdAndTokensBySave = AlarmHistoryMapper.getNewUploadSaveInfo(newUploadInfos);
        // 알림 보내기
        eventPublisher.publishEvent(new MultiFcmEvent(title, message, memberIdAndTokensByPush, memberIdAndTokensBySave, createIdInfo(team.getTeamId(), mission.getId(),mission.getType(),mission.getStatus()), team.getName(), AlarmType.NEW_UPLOAD, PagePath.MISSION_PATH.getValue()));
    }

    private String createIdInfo(Long teamId, Long missionId,MissionType type, MissionStatus status) {
        JSONObject jo = new JSONObject();
        jo.put("isRepeated", type.equals(MissionType.REPEAT));
        jo.put("teamId", teamId);
        jo.put("missionId", missionId);
        jo.put("status", status.name());
        jo.put("type", "COMPLETE_MISSION");
        return jo.toJSONString();
    }
}

