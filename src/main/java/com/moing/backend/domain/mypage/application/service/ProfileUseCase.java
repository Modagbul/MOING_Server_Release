package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.request.UpdateProfileRequest;
import com.moing.backend.domain.mypage.application.dto.response.GetProfileResponse;
import com.moing.backend.global.config.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileUseCase {

    private final MemberGetService memberGetService;
    private final S3Service s3Service;

    public GetProfileResponse getProfile(String socialId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return new GetProfileResponse(member.getProfileImage(), member.getNickName(), member.getIntroduction());
    }

    @Transactional
    public void updateProfile(String socialId, UpdateProfileRequest updateProfileRequest) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        deletePreviousImage(updateProfileRequest, member);
        member.updateProfile(
                getUpdatedValue(updateProfileRequest.getProfileImage(), member.getProfileImage()),
                getUpdatedValue(updateProfileRequest.getNickName(), member.getNickName()),
                getUpdatedValue(updateProfileRequest.getIntroduction(), member.getIntroduction())
        );
    }

    private String getUpdatedValue(String newValue, String currentValue) {
        if (newValue != null) {
            return newValue;
        }
        return currentValue;
    }

    private void deletePreviousImage(UpdateProfileRequest updateProfileRequest, Member member) {
        if (updateProfileRequest.getProfileImage() != null) {
            s3Service.deleteImage(member.getProfileImage());
        }
    }

}
