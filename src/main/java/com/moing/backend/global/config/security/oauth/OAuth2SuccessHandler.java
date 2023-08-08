package com.moing.backend.global.config.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moing.backend.domain.auth.application.dto.response.SignInResponse;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberSaveService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import com.moing.backend.global.response.TokenInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final MemberSaveService memberSaveService;
    private final TokenUtil tokenUtil;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Member member = Member.valueOf(oAuth2User);

        // 회원가입(가입 정보 없는 유저일 때만) 및 로그인
        Member signInMember = memberSaveService.saveMember(member);

        // 토큰 생성
        TokenInfoResponse token = tokenUtil.createToken(signInMember, signInMember.getRegistrationStatus().equals(RegistrationStatus.COMPLETED));
        log.info("{}", token);

        // Redis에 Refresh Token 저장
        tokenUtil.storeRefreshToken(signInMember.getSocialId(), token);

        // Response message 생성
        writeOauthResponse(response, SignInResponse.from(token, signInMember.getRegistrationStatus()));
    }

    private void writeOauthResponse(HttpServletResponse response, SignInResponse signInResponse)
            throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        // Httpbody에 json 형태로 로그인 내용 추가
        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(signInResponse));
        writer.flush();
    }

}

