package com.moing.backend.domain.team.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetTeamDetailResponse {
    private Integer boardNum; //안 읽은 게시글
    private TeamInfo teamInfo;
}
