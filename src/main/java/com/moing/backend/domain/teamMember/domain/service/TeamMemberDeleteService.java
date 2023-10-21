package com.moing.backend.domain.teamMember.domain.service;

import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TeamMemberDeleteService {

    private final TeamMemberRepository teamMemberRepository;

    public void deleteAllTeamMembers(Long teamId){
        teamMemberRepository.deleteTeamMembers(teamId);
    }
}
