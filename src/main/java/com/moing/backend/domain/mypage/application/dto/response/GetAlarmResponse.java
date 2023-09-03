package com.moing.backend.domain.mypage.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetAlarmResponse {

    private boolean isNewUploadPush;
    private boolean isRemindPush;
    private boolean isFirePush;
}
