package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.auth.exception.NicknameDuplicationException;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberCheckService;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.request.UpdateProfileRequest;
import com.moing.backend.domain.mypage.application.dto.response.GetProfileResponse;
import com.moing.backend.global.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileUseCase {

    private final MemberGetService memberGetService;
    private final UpdateUtils updateUtils;
    private final MemberCheckService memberCheckService;

    @Transactional(readOnly = true)
    public GetProfileResponse getProfile(String socialId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return new GetProfileResponse(member.getProfileImage(), member.getNickName(), member.getIntroduction());
    }

    @Transactional
    public void updateProfile(String socialId, UpdateProfileRequest updateProfileRequest) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        String oldProfileImageUrl = member.getProfileImage();

        member.updateProfile(
                UpdateUtils.getUpdatedValue(updateProfileRequest.getProfileImage(), member.getProfileImage()),
                UpdateUtils.getUpdatedValue(updateProfileRequest.getNickName(), member.getNickName()),
                UpdateUtils.getUpdatedValue(updateProfileRequest.getIntroduction(), member.getIntroduction())
        );

        if(updateProfileRequest.getNickName()!=null){
            if(memberCheckService.checkNickname(updateProfileRequest.getNickName())) throw new NicknameDuplicationException(); //닉네임 중복검사 (이중체크)
        }

        updateUtils.deleteOldImgUrl(updateProfileRequest.getProfileImage(), oldProfileImageUrl);
    }

}
