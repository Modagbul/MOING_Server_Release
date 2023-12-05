package com.moing.backend.domain.auth.application.service.apple;

import com.moing.backend.domain.auth.application.service.SignInProvider;
import com.moing.backend.domain.member.application.mapper.MemberMapper;
import com.moing.backend.domain.member.domain.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("appleSignIn")
@AllArgsConstructor
public class AppleSignInUseCase implements SignInProvider {

    private final AppleTokenUseCase appleTokenUseCase;
    private final MemberMapper memberMapper;


    public Member getUserData(String identityToken) {
        Jws<Claims> oidcTokenJws = appleTokenUseCase.sigVerificationAndGetJws(identityToken);

        String socialId = oidcTokenJws.getBody().getSubject();
        String email = (String) oidcTokenJws.getBody().get("email");

        return memberMapper.createAppleMember(socialId, email);
    }
}
