package com.moing.backend.domain.team.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamMemberInfo {
    private Long memberId;
    private String nickName;
    private String profileImage;
    private String introduction;
    private Boolean isLeader;

    @QueryProjection
    public TeamMemberInfo(Long memberId, String nickName, String profileImage, String introduction, Long leaderId){
        this.memberId=memberId;
        this.nickName=nickName;
        this.profileImage=profileImage;
        this.introduction=introduction;
        this.isLeader=memberId.equals(leaderId);
    }
}
