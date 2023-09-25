package com.moing.backend.domain.team.application.dto.response;

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

    public GetTeamResponse(Integer numOfTeam, List<TeamBlock> teamBlocks) {
        this.numOfTeam=numOfTeam;
        this.teamBlocks = teamBlocks;
    }
    public void updateMemberNickName(String memberNickName) {
        this.memberNickName=memberNickName;
    }
}


