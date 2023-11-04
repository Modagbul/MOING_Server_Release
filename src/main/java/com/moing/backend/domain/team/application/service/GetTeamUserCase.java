package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.response.GetCurrentStatusResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamDetailResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.application.dto.response.TeamMemberInfo;
import com.moing.backend.domain.team.application.mapper.TeamMapper;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GetTeamUserCase {
    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final BoardGetService boardGetService;
    private final TeamMemberGetService teamMemberGetService;
    private final TeamMapper teamMapper;

    public GetTeamResponse getTeam(String socialId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        return teamGetService.getTeamByMember(member);
    }

    public GetTeamDetailResponse getTeamDetailResponse(String socialId, Long teamId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Integer boardNum = boardGetService.getUnReadBoardNum(teamId, member.getMemberId());
        List<TeamMemberInfo> teamMemberInfoList = teamMemberGetService.getTeamMemberInfo(teamId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        return teamMapper.toTeamDetailResponse(team, boardNum, teamMemberInfoList);
    }

    public GetCurrentStatusResponse getCurrentStatus(Long teamId) {
        Team team=teamGetService.getTeamByTeamId(teamId);
        return teamMapper.toCurrentStatusResponse(team);
    }
}
