package com.moing.backend.domain.history.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberIdAndToken {

    private String fcmToken;
    private Long memberId;

}
