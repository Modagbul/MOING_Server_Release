package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.GetBoardDetailResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.boardRead.application.mapper.BoardReadMapper;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.boardRead.domain.service.BoardReadSaveService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetBoardUserCase {

    private final MemberGetService memberGetService;
    private final BoardGetService boardGetService;
    private final BoardMapper boardMapper;
    private final TeamGetService teamGetService;
    private final BoardReadMapper boardReadMapper;
    private final BoardReadSaveService boardReadSaveService;
    private final TeamMemberGetService teamMemberGetService;

    public GetBoardDetailResponse getBoardDetail(String socialId, Long teamId, Long boardId) {
        Member member=memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        Board board = boardGetService.getBoard(boardId);
        TeamMember teamMember=teamMemberGetService.getTeamMember(member, team);
        //읽음 처리
        BoardRead boardRead = boardReadMapper.toBoardRead(team, member);
        boardReadSaveService.saveBoardRead(board, boardRead);

        return boardMapper.toBoardDetail(board, teamMember==board.getTeamMember());
    }

    public GetAllBoardResponse getAllBoard(String socialId, Long teamId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return boardGetService.getBoardAll(teamId, member.getMemberId());
    }
}
