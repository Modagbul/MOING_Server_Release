package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.history.domain.service.AlarmHistorySaveService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.event.FcmEvent;
import com.moing.backend.global.response.BaseServiceResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.moing.backend.global.config.fcm.constant.NewNoticeUploadMessage.NEW_NOTICE_UPLOAD_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class SendBoardAlarmUseCase {

    private final TeamMemberGetService teamMemberGetService;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmHistorySaveService alarmHistorySaveService;
    private final AlarmHistoryMapper alarmHistoryMapper;

    public void sendNewUploadAlarm(BaseServiceResponse baseServiceResponse, Board board) {
        Member member = baseServiceResponse.getMember();
        Team team = baseServiceResponse.getTeam();

        if (board.isNotice() && member.isNewUploadPush()) {
            String title = NEW_NOTICE_UPLOAD_MESSAGE.title(team.getName());
            String body = NEW_NOTICE_UPLOAD_MESSAGE.body(board.getTitle());
            Optional<List<MemberIdAndToken>> memberIdAndTokens = teamMemberGetService.getMemberInfoExceptMe(team.getTeamId(), member.getMemberId());
            if (memberIdAndTokens.isPresent() && !memberIdAndTokens.get().isEmpty()) {
                //알림 보내기
                List<String> fcmTokens = getFcmTokens(memberIdAndTokens);
                eventPublisher.publishEvent(new FcmEvent(title, body, fcmTokens));
                //알림 저장하기
                List<Long> memberIds = getMemberIds(memberIdAndTokens);
                String idInfo = createIdInfo(team.getTeamId(), board.getBoardId());
                saveAlarmHistory(idInfo, memberIds, title, body, team.getName());
            }
        }
    }

    private List<String> getFcmTokens(Optional<List<MemberIdAndToken>> memberIdAndTokens) {
        return memberIdAndTokens.map(list -> list.stream()
                        .map(MemberIdAndToken::getFcmToken)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private List<Long> getMemberIds(Optional<List<MemberIdAndToken>> memberIdAndTokens) {
        return memberIdAndTokens.map(list -> list.stream()
                        .map(MemberIdAndToken::getMemberId)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private void saveAlarmHistory(String idInfo, List<Long> memberIds, String title, String body, String teamName) {
        List<AlarmHistory> alarmHistories = memberIds.stream()
                .map(memberId -> alarmHistoryMapper.toAlarmHistory(AlarmType.NEW_UPLOAD, PagePath.NOTICE_PATH.getValue(), idInfo, memberId, title, body, teamName))
                .collect(Collectors.toList());

        alarmHistorySaveService.saveAlarmHistory(alarmHistories); // 여기서 saveAll은 일괄 삽입을 처리하는 메서드입니다.
    }

    private String createIdInfo(Long teamId, Long boardId) {
        JSONObject jo = new JSONObject();
        jo.put("teamId", teamId);
        jo.put("boardId", boardId);
        return jo.toJSONString();
    }

}

