package com.moing.backend.domain.boardRead.application.mapper;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class BoardReadMapper {
    public BoardRead toBoardRead(Team team, Member member){
        BoardRead boardRead=new BoardRead();
        boardRead.updateTeam(team);
        boardRead.updateMember(member);
        return boardRead;
    }
}
