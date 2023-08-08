package com.moing.backend.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfoResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

    public static TokenInfoResponse from(String grantType, String accessToken, String refreshToken, Long refreshTokenExpirationTime) {
        return TokenInfoResponse.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken=refreshToken;
    }
}

