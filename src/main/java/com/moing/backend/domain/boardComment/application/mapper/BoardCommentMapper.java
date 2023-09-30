package com.moing.backend.domain.boardComment.application.mapper;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.boardComment.application.dto.request.CreateBoardCommentRequest;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCommentMapper {
    public BoardComment toBoardComment(TeamMember teamMember, Board board, CreateBoardCommentRequest createBoardCommentRequest){
        BoardComment boardComment= BoardComment.builder()
                .content(createBoardCommentRequest.getContent())
                .build();
        boardComment.updateBoard(board);
        boardComment.updateTeamMember(teamMember);
        return boardComment;
    }
}
