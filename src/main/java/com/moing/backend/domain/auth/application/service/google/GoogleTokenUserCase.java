package com.moing.backend.domain.auth.application.service.google;

import com.moing.backend.domain.auth.exception.AppIdInvalidException;
import com.moing.backend.domain.auth.exception.TokenInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class GoogleTokenUserCase {

    @Value("${oauth2.google.appId}")
    private String appId;

    public void verifyAccessToken(String aud) {
        String extractedAppId = Arrays.stream(aud.split("-"))
                .findFirst()
                .orElseThrow(TokenInvalidException::new);

        if (!appId.equals(extractedAppId)) throw new AppIdInvalidException();
    }
}
