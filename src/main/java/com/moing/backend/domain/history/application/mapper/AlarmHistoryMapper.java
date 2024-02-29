package com.moing.backend.domain.history.application.mapper;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
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

    public static AlarmHistory toAlarmHistory(AlarmType type, String path, String idInfo, Long receiverId, String title, String body, String name){
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

    public static List<String> getFcmTokens(List<MemberIdAndToken> memberIdAndTokens) {
        if (memberIdAndTokens == null || memberIdAndTokens.isEmpty()) {
            return Collections.emptyList();
        }

        return memberIdAndTokens.stream()
                .map(MemberIdAndToken::getFcmToken)
                .collect(Collectors.toList());
    }


    public static List<Long> getMemberIds(List<MemberIdAndToken> memberIdAndTokens) {
        if (memberIdAndTokens == null || memberIdAndTokens.isEmpty()) {
            return Collections.emptyList();
        }

        return memberIdAndTokens.stream()
                .map(MemberIdAndToken::getMemberId)
                .collect(Collectors.toList());
    }

    public static List<AlarmHistory> getAlarmHistories(String idInfo, List<Long> memberIds, String title, String body, String teamName, AlarmType alarmType, String path) {
        return memberIds.stream()
                .map(memberId -> toAlarmHistory(alarmType, path, idInfo, memberId, title, body, teamName))
                .collect(Collectors.toList());
    }

    public static Optional<List<MemberIdAndToken>> getNewUploadSaveInfo(Optional<List<NewUploadInfo>> optionalNewUploadInfos) {
        return optionalNewUploadInfos.map(newUploadInfos ->
                newUploadInfos.stream()
                        .map(info -> new MemberIdAndToken(info.getFcmToken(), info.getMemberId()))
                        .collect(Collectors.toList())
        );
    }

    public static Optional<List<MemberIdAndToken>> getNewUploadPushInfo(Optional<List<NewUploadInfo>> optionalNewUploadInfos) {
        return optionalNewUploadInfos.map(newUploadInfos ->
                newUploadInfos.stream()
                        .filter(NewUploadInfo::isNewUploadPush)
                        .filter(info -> !info.isSignOut())
                        .map(info -> new MemberIdAndToken(info.getFcmToken(), info.getMemberId()))
                        .collect(Collectors.toList())
        );
    }

}
