package com.moing.backend.global.config.fcm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultiResponse {
    private final String response;
    private final List<String> failedTokens;

}