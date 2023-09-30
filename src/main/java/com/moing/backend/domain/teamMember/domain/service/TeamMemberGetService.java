package com.moing.backend.domain.teamMember.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.exception.NotFoundByTeamIdException;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TeamMemberGetService {
    private final TeamMemberRepository teamMemberRepository;

    public List<Long> getTeamMemberIds(Long teamId){
        return teamMemberRepository.findMemberIdsByTeamId(teamId);
    }

    public TeamMember getTeamMember(Member member, Team team){
        return teamMemberRepository.findTeamMemberByTeamAndMember(team, member).orElseThrow(()-> new NotFoundByTeamIdException());
    }
}
