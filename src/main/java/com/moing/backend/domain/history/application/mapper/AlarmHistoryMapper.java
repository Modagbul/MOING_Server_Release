package com.moing.backend.domain.history.application.mapper;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlarmHistoryMapper {

    public AlarmHistory toAlarmHistory(AlarmType type, String path, String idInfo, Long receiverId, String title, String body, String name){
        return AlarmHistory.builder()
                .type(type)
                .path(path)
                .idInfo(idInfo)
                .receiverId(receiverId)
                .title(title)
                .body(body)
                .name(name)
                .isRead(false)
                .build();
    }

    public List<String> getFcmTokens(Optional<List<MemberIdAndToken>> memberIdAndTokens) {
        return memberIdAndTokens.map(list -> list.stream()
                        .map(MemberIdAndToken::getFcmToken)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<Long> getMemberIds(Optional<List<MemberIdAndToken>> memberIdAndTokens) {
        return memberIdAndTokens.map(list -> list.stream()
                        .map(MemberIdAndToken::getMemberId)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<AlarmHistory> getAlarmHistories(String idInfo, List<Long> memberIds, String title, String body, String teamName, AlarmType alarmType, String path) {
        return memberIds.stream()
                .map(memberId -> toAlarmHistory(alarmType, path, idInfo, memberId, title, body, teamName))
                .collect(Collectors.toList());
    }

}
