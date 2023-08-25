package com.moing.backend.domain.teamMember.domain.service;

import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TeamMemberGetService {
    private final TeamMemberRepository teamMemberRepository;

    public List<Long> getTeamMemberIds(Long teamId){
        return teamMemberRepository.getMemberIdsByTeamId(teamId);
    }
}
