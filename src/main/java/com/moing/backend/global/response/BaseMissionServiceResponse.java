package com.moing.backend.global.response;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseMissionServiceResponse {
    private Member member;
    private Team team;
    private MissionArchive missionArchive;
    private TeamMember teamMember;
}
