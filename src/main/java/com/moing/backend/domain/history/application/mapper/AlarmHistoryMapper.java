package com.moing.backend.domain.history.application.mapper;

import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

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
}
