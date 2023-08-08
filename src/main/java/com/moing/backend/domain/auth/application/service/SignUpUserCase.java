package com.moing.backend.domain.auth.application.service;

import com.moing.backend.domain.auth.application.dto.request.SignUpRequest;
import com.moing.backend.domain.auth.application.dto.response.SignInResponse;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberQueryService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import com.moing.backend.global.config.security.util.AuthenticationUtil;
import com.moing.backend.global.response.TokenInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpUserCase {

    private final TokenUtil tokenUtil;
    private final MemberQueryService memberQueryService;

    public SignInResponse signUp(String token, SignUpRequest signUpRequest) {

        //1. 유저 찾기
        String socialId = tokenUtil.getSocialId(token);
        Member member = memberQueryService.getMemberBySocialId(socialId);
        //2. signUp 처리
        member.signUp(signUpRequest.getNickName());
        //3. security 처리
        AuthenticationUtil.makeAuthentication(member);
        //4. token 만들기
        TokenInfoResponse tokenResponse = tokenUtil.createToken(member, member.getRegistrationStatus().equals(RegistrationStatus.COMPLETED));
        //5. refresh token 저장
        tokenUtil.storeRefreshToken(member.getSocialId(), tokenResponse);

        return SignInResponse.from(tokenResponse, member.getRegistrationStatus());
    }

}
