package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.UpdateBoardResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.board.domain.service.BoardSaveService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.service.CheckLeaderUserCase;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateBoardUserCase {
    private final MemberGetService memberGetService;
    private final BoardSaveService boardSaveService;
    private final CheckLeaderUserCase checkLeaderUserCase;
    private final TeamGetService teamGetService;
    private final BoardMapper boardMapper;
    private final BoardGetService boardGetService;

    @Transactional
    public UpdateBoardResponse updateBoard(String socialId, Long teamId, Long boardId, UpdateBoardRequest updateBoardRequest){
        Member member=memberGetService.getMemberBySocialId(socialId);
        Board board=boardGetService.getBoard(boardId);
        board.updateBoard(updateBoardRequest);
        return new UpdateBoardResponse(board.getBoardId());
    }
}
