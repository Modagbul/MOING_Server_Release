package com.moing.backend.domain.team.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetCurrentStatusResponse {

    private String name;

    private String introduction;

    private String profileImgUrl;
}
