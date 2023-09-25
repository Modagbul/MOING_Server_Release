package com.moing.backend.domain.auth.application.service.google;

import com.moing.backend.domain.auth.application.dto.response.GoogleUserResponse;
import com.moing.backend.domain.auth.application.service.SignInProvider;
import com.moing.backend.domain.auth.exception.AppIdInvalidException;
import com.moing.backend.domain.auth.exception.TokenInvalidException;
import com.moing.backend.domain.member.application.mapper.MemberMapper;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.global.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service("google")
@RequiredArgsConstructor
public class GoogleSignInUserCase implements SignInProvider {

    private final MemberMapper memberMapper;
    private final WebClient webClient;
    private final GoogleTokenUserCase googleTokenUserCase;

    public Member getUserData(String accessToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenInvalidException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new InternalServerErrorException("Google Internal Server Error ")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        googleTokenUserCase.verifyAccessToken(googleUserResponse.getAud());

        googleUserResponse.adaptResponse();
        return memberMapper.createGoogleMember(googleUserResponse);
    }


}
