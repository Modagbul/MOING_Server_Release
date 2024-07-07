package com.moing.backend.domain.team.application.dto.response;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.dto.response.UserProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamResponse {
    private String memberNickName;
    private Integer numOfTeam;
    private List<TeamBlock> teamBlocks = new ArrayList<>();
    private UserProperty userProperty;

    public GetTeamResponse(Integer numOfTeam, List<TeamBlock> teamBlocks) {
        this.numOfTeam=numOfTeam;
        this.teamBlocks = teamBlocks;
    }
    public void updateMemberInfo(Member member) {
        this.memberNickName=member.getNickName();
        this.userProperty=new UserProperty(member.getGender(), member.getBirthDate());

    }
}


