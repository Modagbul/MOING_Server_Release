package com.moing.backend.domain.boardComment.application.mapper;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.comment.application.dto.request.CreateCommentRequest;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCommentMapper {
    public static BoardComment toBoardComment(TeamMember teamMember, Board board, CreateCommentRequest createCommentRequest, boolean isLeader) {
        BoardComment boardComment= new BoardComment();
        boardComment.init(createCommentRequest.getContent(),isLeader);
        boardComment.updateBoard(board);
        boardComment.updateTeamMember(teamMember);
        return boardComment;
    }
}
