package com.moing.backend.domain.history.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUploadInfo {

    private String fcmToken;
    private Long memberId;
    private boolean isNewUploadPush;
    private boolean isSignOut;

}
