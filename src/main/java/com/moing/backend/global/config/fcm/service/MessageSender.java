package com.moing.backend.global.config.fcm.service;

public interface MessageSender<T, R> {
    void send(T request);
}
