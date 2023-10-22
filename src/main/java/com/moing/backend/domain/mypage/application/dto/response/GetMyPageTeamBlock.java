package com.moing.backend.domain.mypage.application.dto.response;

import com.moing.backend.domain.team.domain.constant.Category;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetMyPageTeamBlock {
    private Long teamId;
    private String teamName;
    private Category category;
}
