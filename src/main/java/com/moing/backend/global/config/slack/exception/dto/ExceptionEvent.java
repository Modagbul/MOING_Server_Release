package com.moing.backend.global.config.slack.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
@AllArgsConstructor
public class ExceptionEvent {

    private final HttpServletRequest request;
    private final Exception exception;
}
