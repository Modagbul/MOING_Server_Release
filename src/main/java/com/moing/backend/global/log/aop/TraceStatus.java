package com.moing.backend.global.log.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public class TraceStatus {
    private String threadId;
    private Long startTime;
    private String className;
    private String methodName;
}