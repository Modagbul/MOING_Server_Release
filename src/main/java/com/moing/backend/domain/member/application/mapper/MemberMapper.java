package com.moing.backend.domain.member.application.mapper;

import com.moing.backend.domain.auth.application.dto.response.KakaoUserResponse;
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
                .nickName(kakaoUserResponse.getProperties().getNickname())
                .gender(kakaoUserResponse.getKakaoAccount().getGender())
                .ageRange(kakaoUserResponse.getKakaoAccount().getAgeRange())
                .role(Role.USER)
                .build();

        return newMember;
    }

    public Member createAppleMember(String socialId, String email) {
        Member newMember = Member.builder()
                .socialId(SocialProvider.APPLE + "@" + socialId)
                .provider(SocialProvider.APPLE)
                .email(email)
                .role(Role.USER)
                .build();

        return newMember;
    }
}
