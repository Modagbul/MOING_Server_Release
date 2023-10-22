package com.moing.backend.domain.mypage.application.dto.response;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetMyPageResponse {
    private String profileImage;
    private String nickName;
    private String introduction;
    private List<Category> categories=new ArrayList<>();
    private List<GetMyPageTeamBlock> getMyPageTeamBlocks = new ArrayList<>();
}