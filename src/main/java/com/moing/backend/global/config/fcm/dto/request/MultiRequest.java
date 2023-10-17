package com.moing.backend.global.config.fcm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MultiRequest {
    @NotNull(message = "기기 등록 토큰들을 입력해 주세요.")
    private List<String> registrationToken;

    @NotNull(message = "알림 제목을 입력해 주세요.")
    private String title;

    @NotNull(message = "알림 내용을 입력해 주세요.")
    private String body;

}
