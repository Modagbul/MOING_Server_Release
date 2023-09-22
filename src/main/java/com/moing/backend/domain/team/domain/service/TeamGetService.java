package com.moing.backend.domain.team.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.repository.TeamRepository;
import com.moing.backend.domain.team.exception.NotFoundByTeamIdException;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class TeamGetService {
    private final TeamRepository teamRepository;

    public GetTeamResponse getTeamByMember(Member member) {
        GetTeamResponse getTeamResponse = teamRepository.findTeamByMemberId(member.getMemberId());
        getTeamResponse.updateMemberNickName(member.getNickName());
        return getTeamResponse;
    }

    public Team getTeamByTeamId(Long teamId){
        return teamRepository.findTeamByTeamId(teamId).orElseThrow(()->new NotFoundByTeamIdException());
    }
}
