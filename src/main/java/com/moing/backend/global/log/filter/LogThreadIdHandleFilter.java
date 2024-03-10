package com.moing.backend.global.log.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moing.backend.global.log.aop.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
@Order(Integer.MIN_VALUE)
@Component
@RequiredArgsConstructor
public class LogThreadIdHandleFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final LogTrace logTrace;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logTrace.configThreadId();
        log.info(buildRequestInfoMessage((HttpServletRequest) request));
        chain.doFilter(request, response);
        logTrace.clearTheadId();
    }

    private String buildRequestInfoMessage(HttpServletRequest request) throws JsonProcessingException {
        final RequestInfoFormat requestInfoFormat = RequestInfoFormat.builder()
                .threadId(logTrace.getThreadId())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build();
        return objectMapper.writeValueAsString(requestInfoFormat);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}