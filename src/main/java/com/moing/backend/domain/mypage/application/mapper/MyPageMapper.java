package com.moing.backend.domain.mypage.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageResponse;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyPageMapper {

    public static GetMyPageResponse toGetMyPageResponse(Member member, List<String> categories, List<GetMyPageTeamBlock> blocks) {
        return GetMyPageResponse.builder()
                .profileImage(member.getProfileImage())
                .nickName(member.getNickName())
                .introduction(member.getIntroduction())
                .categories(categories)
                .getMyPageTeamBlocks(blocks)
                .build();
    }
}
