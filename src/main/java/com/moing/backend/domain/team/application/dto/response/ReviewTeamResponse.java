package com.moing.backend.domain.team.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTeamResponse {
    private Long teamId;
    private String teamName;
    private Integer numOfMember;
    private Long duration; //걸린시간(단위:날짜)
    private Long numOfMission;
    private Integer levelOfFire; //불꽃 레벨
}