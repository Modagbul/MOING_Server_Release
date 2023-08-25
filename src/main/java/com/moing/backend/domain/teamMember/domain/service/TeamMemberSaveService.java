package com.moing.backend.domain.teamMember.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TeamMemberSaveService {
    private final TeamMemberRepository teamMemberRepository;
    public void saveTeamMember(Team team, Member member){
        TeamMember teamMember=new TeamMember();
        teamMember.updateMember(member);
        teamMember.updateTeam(team);
        this.teamMemberRepository.save(teamMember);
    }
}
