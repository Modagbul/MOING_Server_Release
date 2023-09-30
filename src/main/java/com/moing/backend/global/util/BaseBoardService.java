package com.moing.backend.global.util;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BaseBoardService {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final BoardGetService boardGetService;
    private final TeamMemberGetService teamMemberGetService;

    public BaseBoardServiceResponse getCommonData(String socialId, Long teamId, Long boardId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        Board board = boardGetService.getBoard(boardId);
        TeamMember teamMember = teamMemberGetService.getTeamMember(member, team);

        return new BaseBoardServiceResponse(member, team, board, teamMember);
    }
}