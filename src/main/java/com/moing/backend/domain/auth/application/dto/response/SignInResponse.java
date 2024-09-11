package com.moing.backend.domain.auth.application.dto.response;

import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.global.response.TokenInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean registrationStatus;

    public static SignInResponse from(TokenInfoResponse tokenInfoResponse, RegistrationStatus registrationStatus) {
        return SignInResponse.builder()
                .accessToken(tokenInfoResponse.getAccessToken())
                .refreshToken(tokenInfoResponse.getRefreshToken())
                .registrationStatus(registrationStatus.equals(RegistrationStatus.COMPLETED))
                .build();
    }
}
