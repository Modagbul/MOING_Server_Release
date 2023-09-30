package com.moing.backend.domain.boardComment.application.service;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.board.exception.NotAuthByBoardException;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentDeleteService;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentGetService;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentSaveService;
import com.moing.backend.domain.boardComment.exception.NotAuthByBoardCommentException;
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
public class DeleteBoardCommentUserCase {
    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final TeamMemberGetService teamMemberGetService;
    private final BoardCommentGetService boardCommentGetService;
    private final BoardCommentDeleteService boardCommentDeleteService;

    public void deleteBoardComment(String socialId, Long teamId, Long boardId, Long boardCommentId){
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        TeamMember teamMember = teamMemberGetService.getTeamMember(member, team);
        BoardComment boardComment=boardCommentGetService.getBoardComment(boardCommentId);
        if (teamMember == boardComment.getTeamMember()) {
            boardCommentDeleteService.deleteBoardComment(boardComment);
        } else throw new NotAuthByBoardCommentException();
    }
}
