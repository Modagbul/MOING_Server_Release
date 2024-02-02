package com.moing.backend.domain.mypage.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetMyPageTeamBlock {
    private Long teamId;
    private String teamName;
    private String category;
    private String profileImgUrl;
}
