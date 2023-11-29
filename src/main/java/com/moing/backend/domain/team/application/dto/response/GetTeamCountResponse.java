package com.moing.backend.domain.team.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetTeamCountResponse {

    private String teamName;
    private Long numOfTeam;
    private String leaderName;

    public void updateCount(Long count){
        this.numOfTeam=count;
    }

    @QueryProjection
    public GetTeamCountResponse(String teamName, String leaderName){
        this.teamName=teamName;
        this.leaderName=leaderName;
    }
}
