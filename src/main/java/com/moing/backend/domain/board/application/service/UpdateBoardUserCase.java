package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.UpdateBoardResponse;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.board.exception.NotAuthByBoardException;
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
public class UpdateBoardUserCase {
    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final BoardGetService boardGetService;
    private final TeamMemberGetService teamMemberGetService;

    /**
     * 게시글 수정
     */
    public UpdateBoardResponse updateBoard(String socialId, Long teamId, Long boardId, UpdateBoardRequest updateBoardRequest){
        Member member=memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        Board board = boardGetService.getBoard(boardId);
        TeamMember teamMember=teamMemberGetService.getTeamMember(member, team);
        if (teamMember == board.getTeamMember()) {
            board.updateBoard(updateBoardRequest);
            return new UpdateBoardResponse(board.getBoardId());
        } else throw new NotAuthByBoardException();
    }
}
