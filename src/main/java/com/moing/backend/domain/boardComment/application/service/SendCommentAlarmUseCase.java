package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
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

import java.util.Objects;

import static com.moing.backend.global.config.fcm.constant.NewCommentUploadMessage.NEW_COMMENT_UPLOAD_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class SendCommentAlarmUseCase {

    private final ApplicationEventPublisher eventPublisher;

    public void sendNewUploadAlarm(BaseBoardServiceResponse response, BoardComment comment) {
        Member member = response.getMember();
        Team team = response.getTeam();
        Board board = response.getBoard();
        Member receiver = board.getTeamMember().getMember();

        String title = NEW_COMMENT_UPLOAD_MESSAGE.title(comment.getContent());
        String body = NEW_COMMENT_UPLOAD_MESSAGE.body(member.getNickName(),board.getTitle());

        // 알림 보내기
        if(checkBoardWriter(receiver, member)){
            eventPublisher.publishEvent(new SingleFcmEvent(receiver, title, body, createIdInfo(team.getTeamId(), board.getBoardId()), team.getName(), AlarmType.NEW_UPLOAD, PagePath.NOTICE_PATH.getValue(), receiver.isNewUploadPush()));
        }
    }

    private String createIdInfo(Long teamId, Long boardId) {
        JSONObject jo = new JSONObject();
        jo.put("teamId", teamId);
        jo.put("boardId", boardId);
        return jo.toJSONString();
    }

    private boolean checkBoardWriter(Member boardWriter, Member commentWriter){
        return !Objects.equals(boardWriter.getMemberId(), commentWriter.getMemberId());
    }
}
