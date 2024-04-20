package com.moing.backend.global.log.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moing.backend.global.exception.ApplicationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogFormat {
    private final String threadId;
    private final String className;
    private final String methodName;
    private final Long executeTime;
    private final String errorCode;
    private final String errorMessage;
    private final Class<? extends Exception> errorClass;
    private final StackTraceElement[] errorStackTrace;

    public static LogFormat createLogFormat(TraceStatus traceStatus) {
        return LogFormat.builder()
                .threadId(traceStatus.getThreadId())
                .className(traceStatus.getClassName())
                .methodName(traceStatus.getMethodName())
                .executeTime(System.currentTimeMillis() - traceStatus.getStartTime())
                .build();
    }

    public static LogFormat createErrorLogFormat(TraceStatus traceStatus, Exception exception) {
        LogFormat.LogFormatBuilder logFormatBuilder = LogFormat.builder()
                .threadId(traceStatus.getThreadId())
                .className(traceStatus.getClassName())
                .methodName(traceStatus.getMethodName());
        if (exception instanceof ApplicationException) {
            ApplicationException ae = (ApplicationException) exception;
            return logFormatBuilder
                    .errorCode(ae.getErrorCode().getErrorCode())
                    .errorMessage(ae.getErrorCode().getMessage())
                    .build();
        } else {
            return logFormatBuilder
                    .errorClass(exception.getClass())
                    .errorMessage(exception.getMessage())
                    .errorStackTrace(exception.getStackTrace())
                    .build();
        }
    }
}