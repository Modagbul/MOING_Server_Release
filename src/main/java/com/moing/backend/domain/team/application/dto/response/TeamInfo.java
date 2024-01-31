package com.moing.backend.domain.team.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TeamInfo {
    private Boolean isDeleted;
    private LocalDateTime deletionTime;
    private String teamName; //소모임 이름
    private Integer numOfMember; //소모임원 수
    private String category; //카테고리
    private String introduction; //소개
    private Long currentUserId; //현재 유저 아이디
    private List<TeamMemberInfo> teamMemberInfoList = new ArrayList<>(); //소모임원 정보
}