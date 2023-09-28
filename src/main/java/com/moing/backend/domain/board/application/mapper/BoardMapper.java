package com.moing.backend.domain.board.application.mapper;

import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardMapper {

    public Board toBoard(Member member, TeamMember teamMember, Team team, CreateBoardRequest createBoardRequest, boolean isLeader) {
        Board board = Board.builder()
                .writerNickName(member.getNickName())
                .writerProfileImage(member.getProfileImage())
                .title(createBoardRequest.getTitle())
                .content(createBoardRequest.getContent())
                .isNotice(createBoardRequest.getIsNotice())
                .commentNum(0)
                .isLeader(isLeader)
                .build();
        board.updateTeamMember(teamMember);
        board.updateTeam(team);
        return board;
    }
}
