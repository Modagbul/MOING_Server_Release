package com.moing.backend.global.config.fcm.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FcmEvent {

    private String title;
    private String body;
    private List<String> tokens;

}
