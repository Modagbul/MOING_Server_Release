package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckLeaderUserCase {
    public boolean isTeamLeader(Member member, Team team) {
        if (member.getMemberId() == team.getLeaderId()) {
            return true;
        } else {
            return false;
        }
    }
}
