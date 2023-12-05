package com.moing.backend.domain.auth.application.service.google;

import com.moing.backend.domain.auth.application.dto.response.GoogleUserResponse;
import com.moing.backend.domain.auth.application.service.SignInProvider;
import com.moing.backend.domain.auth.exception.TokenInvalidException;
import com.moing.backend.domain.member.application.mapper.MemberMapper;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.global.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service("googleSignIn")
@RequiredArgsConstructor
public class GoogleSignInUseCase implements SignInProvider {

    private final MemberMapper memberMapper;
    private final WebClient webClient;
    private final GoogleTokenUseCase googleTokenUseCase;

    public Member getUserData(String accessToken) {
        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenInvalidException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new InternalServerErrorException("Google Internal Server Error ")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        if (googleUserResponse != null) {
            googleTokenUseCase.verifyAccessToken(googleUserResponse.getAud());
            googleUserResponse.adaptResponse();
            return memberMapper.createGoogleMember(googleUserResponse);
        }
        throw new TokenInvalidException();
    }

}
