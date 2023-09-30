package com.moing.backend.global.util;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.response.BaseServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BaseService {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final TeamMemberGetService teamMemberGetService;

    public BaseServiceResponse getCommonData(String socialId, Long teamId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        TeamMember teamMember = teamMemberGetService.getTeamMember(member, team);

        return new BaseServiceResponse(member, team, teamMember);
    }
}
