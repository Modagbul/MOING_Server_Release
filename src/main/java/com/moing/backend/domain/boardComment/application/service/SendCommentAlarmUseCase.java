package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.config.fcm.dto.event.SingleFcmEvent;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.history.domain.entity.PagePath.MISSION_PATH;
import static com.moing.backend.global.config.fcm.constant.NewCommentUploadMessage.NEW_COMMENT_UPLOAD_MESSAGE;
import static com.moing.backend.global.config.fcm.constant.NewNoticeUploadMessage.NEW_NOTICE_UPLOAD_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class SendCommentAlarmUseCase {

    private final ApplicationEventPublisher eventPublisher;

    public void sendNewUploadAlarm(BaseBoardServiceResponse response, BoardComment comment) {
        Member member = response.getMember();
        Team team = response.getTeam();
        Board board = response.getBoard();

        String title = NEW_COMMENT_UPLOAD_MESSAGE.title(comment.getContent());
        String body = NEW_COMMENT_UPLOAD_MESSAGE.body(member.getNickName(),board.getTitle());

        // 알림 보내기
        eventPublisher.publishEvent(new SingleFcmEvent(board.getTeamMember().getMember(), title, body, createIdInfo(team.getTeamId(), board.getBoardId()), team.getName(), AlarmType.NEW_UPLOAD, PagePath.NOTICE_PATH.getValue()));

    }

    private String createIdInfo(Long teamId, Long boardId) {
        JSONObject jo = new JSONObject();
        jo.put("teamId", teamId);
        jo.put("boardId", boardId);
        return jo.toJSONString();
    }
}
