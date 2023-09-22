package com.moing.backend.domain.auth.application.service;

import com.moing.backend.domain.auth.application.dto.response.SignInResponse;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import com.moing.backend.global.config.security.util.AuthenticationUtil;
import com.moing.backend.global.response.TokenInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SignInUserCase {

    private final MemberAuthUserCase internalAuthService;
    private final TokenUtil tokenUtil;
    private final Map<String, SignInProvider> signInProviders;
    private final MemberGetService memberGetService;

    public SignInResponse signIn(String token, String providerInfo) {
        //1. 사용자 정보 가져오기
        Member member = getUserDataFromPlatform(token, providerInfo);
        //2. 로그인 및 회원가입
        Member authenticatedMember = internalAuthService.auth(member, providerInfo);
        //3. security 처리
        AuthenticationUtil.makeAuthentication(authenticatedMember);
        //4. token 만들기
        TokenInfoResponse tokenResponse = tokenUtil.createToken(authenticatedMember, authenticatedMember.getRegistrationStatus().equals(RegistrationStatus.COMPLETED));
        //5. refresh token 저장
        tokenUtil.storeRefreshToken(authenticatedMember.getSocialId(), tokenResponse);

        return SignInResponse.from(tokenResponse, authenticatedMember.getRegistrationStatus());
    }

    private Member getUserDataFromPlatform(String accessToken, String providerInfo) {
        SignInProvider signInProvider = signInProviders.get(providerInfo);
        if (signInProvider == null) {
            throw new IllegalArgumentException("Unknown provider: " + providerInfo);
        }
        return signInProvider.getUserData(accessToken);
    }

    public SignInResponse testSignIn(String socialId, String providerInfo) {
        //1. 사용자 정보 가져오기
        Member member = memberGetService.getMemberBySocialId(socialId);
        //2. 로그인
        Member authenticatedMember = internalAuthService.auth(member, providerInfo);
        //3. security 처리
        AuthenticationUtil.makeAuthentication(authenticatedMember);
        //4. token 만들기
        TokenInfoResponse tokenResponse = tokenUtil.createToken(authenticatedMember, authenticatedMember.getRegistrationStatus().equals(RegistrationStatus.COMPLETED));
        //5. refresh token 저장
        tokenUtil.storeRefreshToken(authenticatedMember.getSocialId(), tokenResponse);

        return SignInResponse.from(tokenResponse, authenticatedMember.getRegistrationStatus());
    }
}
