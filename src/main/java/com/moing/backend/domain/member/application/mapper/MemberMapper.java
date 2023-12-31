package com.moing.backend.domain.member.application.mapper;

import com.moing.backend.domain.auth.application.dto.response.GoogleUserResponse;
import com.moing.backend.domain.auth.application.dto.response.KakaoUserResponse;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.domain.member.domain.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member createKakaoMember(KakaoUserResponse kakaoUserResponse) {
        Member newMember = Member.builder()
                .socialId(SocialProvider.KAKAO + "@" + kakaoUserResponse.getId())
                .provider(SocialProvider.KAKAO)
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .role(Role.USER)
                .registrationStatus(RegistrationStatus.UNCOMPLETED)
                .build();

        return newMember;
    }

    public Member createAppleMember(String socialId, String email) {
        Member newMember = Member.builder()
                .socialId(SocialProvider.APPLE + "@" + socialId)
                .provider(SocialProvider.APPLE)
                .email(email)
                .role(Role.USER)
                .registrationStatus(RegistrationStatus.UNCOMPLETED)
                .build();

        return newMember;
    }

    public Member createGoogleMember(GoogleUserResponse googleUserResponse){
        Member newMember= Member.builder()
                .socialId(SocialProvider.GOOGLE + "@" + googleUserResponse.getSub())
                .provider(SocialProvider.GOOGLE)
                .email(googleUserResponse.getEmail())
                .role(Role.USER)
                .registrationStatus(RegistrationStatus.UNCOMPLETED)
                .build();

        return newMember;
    }
}
