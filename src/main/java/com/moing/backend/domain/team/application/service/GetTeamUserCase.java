package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetTeamUserCase {
    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;

    public GetTeamResponse getTeam(String socialId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        return teamGetService.getTeam(member);
    }
}
