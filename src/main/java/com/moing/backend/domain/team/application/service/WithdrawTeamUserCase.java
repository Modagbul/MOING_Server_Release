package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.team.application.dto.response.DeleteTeamResponse;
import com.moing.backend.domain.team.application.mapper.TeamMapper;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawTeamUserCase {

    private final TeamMemberGetService teamMemberGetService;
    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;

    public DeleteTeamResponse withdrawTeam(String socialId, Long teamId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        TeamMember teamMember = teamMemberGetService.getTeamMember(member, team);
        teamMember.deleteMember(team);
        return new DeleteTeamResponse(teamId);
    }
}
