package com.moing.backend.domain.history.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetAlarmCountResponse {

    private String count;
}
