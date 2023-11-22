package com.moing.backend.domain.team.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetLeaderInfoResponse {

    private Long teamId;
    private String teamName;
    private Long leaderId;
    private String leaderName;
    private String leaderFcmToken;

}
