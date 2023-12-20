package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.response.BaseServiceResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
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

        if (board.isNotice()) {
            String title = NEW_NOTICE_UPLOAD_MESSAGE.title(team.getName());
            String body = NEW_NOTICE_UPLOAD_MESSAGE.body(board.getTitle());
            Optional<List<NewUploadInfo>> newUploadInfos=teamMemberGetService.getNewUploadInfo(team.getTeamId(), member.getMemberId());
            Optional<List<MemberIdAndToken>> memberIdAndTokensByPush = AlarmHistoryMapper.getNewUploadPushInfo(newUploadInfos);
            Optional<List<MemberIdAndToken>> memberIdAndTokensBySave = AlarmHistoryMapper.getNewUploadSaveInfo(newUploadInfos);
            // 알림 보내기
            eventPublisher.publishEvent(new MultiFcmEvent(title, body, memberIdAndTokensByPush, memberIdAndTokensBySave, createIdInfo(team.getTeamId(), board.getBoardId()), team.getName(), AlarmType.NEW_UPLOAD, PagePath.NOTICE_PATH.getValue()));
        }
    }

    private String createIdInfo(Long teamId, Long boardId) {
        JSONObject jo = new JSONObject();
        jo.put("teamId", teamId);
        jo.put("boardId", boardId);
        return jo.toJSONString();
    }

}

