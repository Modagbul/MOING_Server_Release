package com.moing.backend.global.config.slack.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamCreateEvent {

    private final String teamName;
    private final Long leaderId;

}
