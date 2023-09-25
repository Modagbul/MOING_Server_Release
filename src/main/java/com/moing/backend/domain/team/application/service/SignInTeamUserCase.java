package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.response.CreateTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignInTeamUserCase {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final TeamMemberSaveService teamMemberSaveService;
    public CreateTeamResponse signInTeam(String socialId, Long teamId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        Team team=teamGetService.getTeamByTeamId(teamId);
        teamMemberSaveService.addTeamMember(team, member);
        return new CreateTeamResponse(team.getTeamId());
    }
}
