package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.boardComment.application.dto.request.CreateBoardCommentRequest;
import com.moing.backend.domain.boardComment.application.dto.response.CreateBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.mapper.BoardCommentMapper;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentSaveService;
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
@RequiredArgsConstructor
@Transactional
public class CreateBoardCommentUserCase {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final BoardGetService boardGetService;
    private final TeamMemberGetService teamMemberGetService;
    private final BoardCommentSaveService boardCommentSaveService;
    private final BoardCommentMapper boardCommentMapper;

    public CreateBoardCommentResponse createBoardComment(String socialId, Long teamId, Long boardId, CreateBoardCommentRequest createBoardCommentRequest) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        Board board = boardGetService.getBoard(boardId);
        TeamMember teamMember = teamMemberGetService.getTeamMember(member, team);
        BoardComment boardComment = boardCommentSaveService.saveBoardComment(boardCommentMapper.toBoardComment(teamMember, board, createBoardCommentRequest));
        board.incrComNum();
        return new CreateBoardCommentResponse(boardComment.getBoardCommentId());
    }
}
