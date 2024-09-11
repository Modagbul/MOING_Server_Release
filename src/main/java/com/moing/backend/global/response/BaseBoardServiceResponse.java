package com.moing.backend.global.response;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseBoardServiceResponse {
    private Member member;
    private Team team;
    private Board board;
    private TeamMember teamMember;
}
