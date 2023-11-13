package com.moing.backend.domain.auth.application.service.kakao;

import com.moing.backend.domain.auth.application.dto.response.KakaoUserResponse;
import com.moing.backend.domain.auth.application.service.SignInProvider;
import com.moing.backend.domain.member.application.mapper.MemberMapper;
import com.moing.backend.domain.auth.exception.TokenInvalidException;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.global.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service("kakaoSignIn")
@RequiredArgsConstructor
public class KakaoSignInUserCase implements SignInProvider {

    private final WebClient webClient;
    private final MemberMapper memberMapper;
    private final KakaoTokenUserCase kakaoTokenUserCase;

    public Member getUserData(String accessToken) {

        kakaoTokenUserCase.verifyAccessToken(accessToken);


        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenInvalidException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new InternalServerErrorException("Kakao Internal Server Error")))
                .bodyToMono(KakaoUserResponse.class)
                .block();
        kakaoUserResponse.adaptResponse();

        return memberMapper.createKakaoMember(kakaoUserResponse);
    }
}
