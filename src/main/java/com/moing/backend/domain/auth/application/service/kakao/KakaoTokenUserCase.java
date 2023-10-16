package com.moing.backend.domain.auth.application.service.kakao;

import com.moing.backend.domain.auth.application.dto.response.KakaoAccessTokenResponse;
import com.moing.backend.domain.auth.exception.AppIdInvalidException;
import com.moing.backend.domain.auth.exception.TokenInvalidException;
import com.moing.backend.global.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KakaoTokenUserCase {

    @Value("${app-id.kakao}")
    private String appId;

    private final WebClient webClient;

    public void verifyAccessToken(String accessToken) {

        KakaoAccessTokenResponse kakaoAccessTokenResponse = webClient.get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenInvalidException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new InternalServerErrorException("Kakao Internal Server Error ")))
                .bodyToMono(KakaoAccessTokenResponse.class)
                .block();

        if (!kakaoAccessTokenResponse.getAppId().equals(appId)) throw new AppIdInvalidException();

    }
}
