package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.request.UpdateProfileRequest;
import com.moing.backend.domain.mypage.application.dto.response.GetProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileUserCase {

    private final MemberGetService memberGetService;

    public GetProfileResponse getProfile(String socialId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return new GetProfileResponse(member.getProfileImage(), member.getNickName(), member.getIntroduction());
    }

    @Transactional
    public void updateProfile(String socialId, UpdateProfileRequest updateProfileRequest) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        member.updateProfile(
                getUpdatedValue(updateProfileRequest.getProfileImage(), member.getProfileImage()),
                getUpdatedValue(updateProfileRequest.getNickName(), member.getNickName()),
                getUpdatedValue(updateProfileRequest.getIntroduction(), member.getIntroduction())
        );
    }

    private String getUpdatedValue(String newValue, String currentValue) {
        return newValue != null ? newValue : currentValue;
    }

}