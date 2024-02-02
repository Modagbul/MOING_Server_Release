package com.moing.backend.domain.mypage.application.dto.response;

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
    private List<String> categories=new ArrayList<>();
    private List<GetMyPageTeamBlock> getMyPageTeamBlocks = new ArrayList<>();
}