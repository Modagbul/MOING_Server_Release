package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentGetService;
import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.config.fcm.dto.event.SingleFcmEvent;
import com.moing.backend.global.response.BaseBoardServiceResponse;
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
public class SendBoardCommentAlarmUseCase {

    private final ApplicationEventPublisher eventPublisher;
    private final BoardCommentGetService boardCommentGetService;

    public void sendCommentAlarm(BaseBoardServiceResponse response, BoardComment comment) {
        Member member = response.getMember();
        Team team = response.getTeam();
        Board board = response.getBoard();

        Optional<List<NewUploadInfo>> newUploadInfos = boardCommentGetService.getNewUploadInfo(member.getMemberId(), board.getBoardId());
        String title = NEW_COMMENT_UPLOAD_MESSAGE.title(comment.getContent());
        String body = NEW_COMMENT_UPLOAD_MESSAGE.body(member.getNickName(),board.getTitle());

        sendBoardCommentWriter(board, member, title, body, team, newUploadInfos);
        sendBoardWriter(board, member, title, body, team, newUploadInfos);
    }

    private void sendBoardWriter(Board board, Member member, String title, String body, Team team, Optional<List<NewUploadInfo>> newUploadInfos) {
        Member receiver = board.getTeamMember().getMember();

        if (checkBoardWriter(receiver, member, newUploadInfos)) {
            eventPublisher.publishEvent(new SingleFcmEvent(receiver, title, body, createIdInfo(team.getTeamId(), board.getBoardId()), team.getName(), AlarmType.COMMENT, PagePath.NOTICE_PATH.getValue(), receiver.isCommentPush()));
        }
    }

    private void sendBoardCommentWriter(Board board, Member member, String title, String body, Team team, Optional<List<NewUploadInfo>> newUploadInfos) {
        Optional<List<MemberIdAndToken>> memberIdAndTokensByPush = AlarmHistoryMapper.getNewUploadPushInfo(newUploadInfos);
        Optional<List<MemberIdAndToken>> memberIdAndTokensBySave = AlarmHistoryMapper.getNewUploadSaveInfo(newUploadInfos);

        eventPublisher.publishEvent(new MultiFcmEvent(title, body, memberIdAndTokensByPush, memberIdAndTokensBySave, createIdInfo(team.getTeamId(), board.getBoardId()), team.getName(), AlarmType.COMMENT, PagePath.NOTICE_PATH.getValue()));
    }

    private String createIdInfo(Long teamId, Long boardId) {
        JSONObject jo = new JSONObject();
        jo.put("teamId", teamId);
        jo.put("boardId", boardId);
        jo.put("type", "COMMENT");
        return jo.toJSONString();
    }

    private boolean checkBoardWriter(Member boardWriter, Member commentWriter, Optional<List<NewUploadInfo>> newUploadInfos) {
        // 댓글 작성자와 게시글 작성자가 동일한 경우 알림을 보내지 않는다.
        if (Objects.equals(boardWriter.getMemberId(), commentWriter.getMemberId())) {
            return false;
        }

        // newUploadInfos 리스트에 boardWriter의 memberId가 없는 경우만 알림을 보낸다.
        // 리스트가 비어있거나, boardWriter의 memberId가 리스트에 없으면 true를 반환.
        return newUploadInfos
                .map(infos -> infos.stream().noneMatch(info -> info.getMemberId().equals(boardWriter.getMemberId())))
                .orElse(true);
    }


}
