package com.moing.backend.domain.team.application.dto.response;

import com.moing.backend.domain.team.domain.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetTeamDetailResponse {
    private Integer boardNum; //안 읽은 게시글
    private TeamInfo teamInfo;
}
