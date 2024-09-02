package com.moing.backend.domain.missionComment.application.service;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.missionComment.domain.service.MissionCommentGetService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.config.fcm.dto.event.SingleFcmEvent;
import com.moing.backend.global.response.BaseMissionServiceResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.moing.backend.global.config.fcm.constant.NewCommentUploadMessage.NEW_COMMENT_UPLOAD_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class SendMissionCommentAlarmUseCase {

    private final ApplicationEventPublisher eventPublisher;
    private final MissionCommentGetService missionCommentGetService;

    public void sendCommentAlarm(BaseMissionServiceResponse response, MissionComment comment) {
        Member member = response.getMember();
        Team team = response.getTeam();
        MissionArchive missionArchive = response.getMissionArchive();
        Mission mission = missionArchive.getMission();

        Optional<List<NewUploadInfo>> newUploadInfos = missionCommentGetService.getNewUploadInfo(member.getMemberId(), missionArchive.getId());
        String title = NEW_COMMENT_UPLOAD_MESSAGE.title(comment.getContent());
        String body = NEW_COMMENT_UPLOAD_MESSAGE.body(member.getNickName(), mission.getTitle());

        sendMissionCommentWriter(mission, missionArchive, title, body, team, newUploadInfos);
        sendMissionWriter(missionArchive, mission, member, title, body, team, newUploadInfos);
    }
    private void sendMissionWriter(MissionArchive missionArchive, Mission mission,
                                   Member member, String title, String body, Team team,
                                   Optional<List<NewUploadInfo>> newUploadInfos) {
        Member receiver = missionArchive.getMember();
        if (checkMemberWriter(receiver, member, newUploadInfos)) {
            eventPublisher.publishEvent(new SingleFcmEvent(receiver, title, body, createIdInfo(team.getTeamId(), mission.getId(), missionArchive.getId(),mission.getType()), team.getName(), AlarmType.COMMENT, PagePath.MISSION_PATH.getValue(), receiver.isCommentPush()));
        }
    }

    private void sendMissionCommentWriter(Mission mission, MissionArchive missionArchive,
                                          String title, String body, Team team,
                                          Optional<List<NewUploadInfo>> newUploadInfos) {
        Optional<List<MemberIdAndToken>> memberIdAndTokensByPush = AlarmHistoryMapper.getNewUploadPushInfo(newUploadInfos);
        Optional<List<MemberIdAndToken>> memberIdAndTokensBySave = AlarmHistoryMapper.getNewUploadSaveInfo(newUploadInfos);

        eventPublisher.publishEvent(new MultiFcmEvent(title, body, memberIdAndTokensByPush, memberIdAndTokensBySave, createIdInfo(team.getTeamId(), mission.getId(), missionArchive.getId(),mission.getType()), team.getName(), AlarmType.COMMENT, PagePath.MISSION_PATH.getValue()));
    }


    private boolean checkMemberWriter(Member missionWriter, Member commentWriter, Optional<List<NewUploadInfo>> newUploadInfos) {
        // 댓글 작성자와 미션 게시글 작성자가 동일한 경우 알림을 보내지 않는다.
        if (Objects.equals(missionWriter.getMemberId(), commentWriter.getMemberId())) {
            return false;
        }

        // newUploadInfos 리스트에 missionWriter memberId가 없는 경우만 알림을 보낸다.
        // 리스트가 비어있거나, missionWriter의 memberId가 리스트에 없으면 true를 반환.
        return newUploadInfos
                .map(infos -> infos.stream().noneMatch(info -> info.getMemberId().equals(missionWriter.getMemberId())))
                .orElse(true);
    }


    private String createIdInfo(Long teamId, Long missionId, Long missionArchiveId, MissionType missionType) {
        JSONObject jo = new JSONObject();
        jo.put("missionArchiveId", missionArchiveId);
        jo.put("teamId", teamId);
        jo.put("missionId", missionId);
        jo.put("type", "COMMENT_MISSION");
        jo.put("isRepeated", missionType.equals(MissionType.REPEAT));
        return jo.toJSONString();
    }
}