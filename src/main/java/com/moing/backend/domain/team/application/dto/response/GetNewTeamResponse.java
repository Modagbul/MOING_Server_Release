package com.moing.backend.domain.team.application.dto.response;

import com.moing.backend.domain.team.domain.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetNewTeamResponse {

    private String teamName;
    private Category category;
    private String promise;
    private String introduction;
    private String profileImgUrl;
    private LocalDateTime createdDate;

}
