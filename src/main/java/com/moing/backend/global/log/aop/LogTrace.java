package com.moing.backend.global.log.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogTrace {

    private static final ThreadLocal<String> threadId = new ThreadLocal<>();
    private static final Integer WARN_REQUEST_TIME = 1000;

    private final ObjectMapper objectMapper;


    public TraceStatus start(String fullClassName, String method) {
        String id = threadId.get();
        long startTime = System.currentTimeMillis();
        int lastDotIndex = fullClassName.lastIndexOf(".");
        String className = fullClassName.substring(lastDotIndex + 1);
        return new TraceStatus(id, startTime, className, method);
    }

    @SneakyThrows
    public void end(TraceStatus traceStatus) {
        final LogFormat logFormat = LogFormat.createLogFormat(traceStatus);
        final String logMessage = objectMapper.writeValueAsString(logFormat);
        if (logFormat.getExecuteTime() >= WARN_REQUEST_TIME) {
            log.warn(logMessage);
        } else {
            log.info(logMessage);
        }
    }

    @SneakyThrows
    public void exception(Exception exception, TraceStatus traceStatus){
        final LogFormat errorLogFormat = LogFormat.createErrorLogFormat(traceStatus, exception);
        final String errorLog = objectMapper.writeValueAsString(errorLogFormat);
        log.error(errorLog);
    }

    public void configThreadId() {
        threadId.set(createThreadId());
    }

    public void clearTheadId() {
        threadId.remove();
    }

    public String getThreadId() {
        return threadId.get();
    }

    private String createThreadId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

}