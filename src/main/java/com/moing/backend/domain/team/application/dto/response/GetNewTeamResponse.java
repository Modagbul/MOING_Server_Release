package com.moing.backend.domain.team.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetNewTeamResponse {

    private String teamName;
    private String category;
    private String promise;
    private String introduction;
    private String profileImgUrl;
    private LocalDateTime createdDate;

}
