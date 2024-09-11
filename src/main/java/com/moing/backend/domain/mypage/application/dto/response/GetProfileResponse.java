package com.moing.backend.domain.mypage.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetProfileResponse {
    private String profileImage;
    private String nickName;
    private String introduction;
}
