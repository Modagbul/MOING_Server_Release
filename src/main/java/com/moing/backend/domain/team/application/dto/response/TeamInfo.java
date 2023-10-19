package com.moing.backend.domain.team.application.dto.response;

import com.moing.backend.domain.team.domain.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TeamInfo {
    private String teamName; //소모임 이름
    private Integer numOfMember; //소모임원 수
    private Category category; //카테고리
    private String introduction; //소개
    private List<TeamMemberInfo> teamMemberInfoList = new ArrayList<>(); //소모임원 정보
}