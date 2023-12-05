package com.moing.backend.domain.history.application.dto.response;

import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class GetAlarmHistoryResponse {

    private Long alarmHistoryId;

    private AlarmType type;

    private String path;

    private String idInfo;

    private String title;

    private String body;

    private String name;

    private boolean isRead;

    private String createdDate;

    @QueryProjection
    public GetAlarmHistoryResponse(Long alarmHistoryId, AlarmType type, String path, String idInfo, String title, String body, String name, boolean isRead, LocalDateTime createdDate) {
        this.alarmHistoryId = alarmHistoryId;
        this.type = type;
        this.path = path;
        this.idInfo = idInfo;
        this.title = title;
        this.body = body;
        this.name = name;
        this.isRead = isRead;
        this.createdDate = formatCreatedDate(createdDate);
    }

    public String formatCreatedDate(LocalDateTime createdDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime midnightOfCreatedDate = createdDate.toLocalDate().atStartOfDay();

        if (currentDateTime.isAfter(midnightOfCreatedDate.plusDays(1))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M월 d일");
            return midnightOfCreatedDate.plusDays(1).format(dateFormatter);
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:mm");
            return currentDateTime.format(timeFormatter);
        }
    }

}
