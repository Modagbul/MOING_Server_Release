package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.CreateBoardResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardSaveService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.service.CheckLeaderUserCase;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.security.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBoardUserCase {

    private final MemberGetService memberGetService;
    private final BoardSaveService boardSaveService;
    private final CheckLeaderUserCase checkLeaderUserCase;
    private final TeamGetService teamGetService;
    private final BoardMapper boardMapper;
    private final TeamMemberGetService teamMemberGetService;

    /**
     * 게시글 생성
     * @param user
     * @param teamId
     * @param createBoardRequest
     * @return CreateBoardResponse
     */
    public CreateBoardResponse createBoard(User user, Long teamId, CreateBoardRequest createBoardRequest) {
        Member member = memberGetService.getMemberBySocialId(user.getSocialId());
        Team team = teamGetService.getTeamByTeamId(teamId);
        TeamMember teamMember = teamMemberGetService.getTeamMember(member, team);
        boolean isLeader = checkLeaderUserCase.isTeamLeader(member, team);
        Board board = boardMapper.toBoard(member, teamMember, team, createBoardRequest, isLeader);
        boardSaveService.saveBoard(board);
        return new CreateBoardResponse(board.getBoardId());
    }
}
