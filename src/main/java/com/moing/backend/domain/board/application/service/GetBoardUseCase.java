package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.GetBoardDetailResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.boardRead.application.service.CreateBoardReadUseCase;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.utils.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetBoardUseCase {


    private final BaseBoardService baseBoardService;
    private final MemberGetService memberGetService;
    private final CreateBoardReadUseCase createBoardReadUseCase;
    private final BoardGetService boardGetService;


    /**
     * 게시글 상세 조회
     */
    @Transactional
    public GetBoardDetailResponse getBoardDetail(String socialId, Long teamId, Long boardId) {
        // 1. 게시글 조회
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        // 2. 읽음 처리
        createBoardReadUseCase.createBoardRead(data.getTeam(), data.getMember(), data.getBoard());
        return BoardMapper.toBoardDetail(data.getBoard(), data.getTeamMember() == data.getBoard().getTeamMember(), data.getBoard().getTeamMember().isDeleted());
    }

    /**
     * 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public GetAllBoardResponse getAllBoard(String socialId, Long teamId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return boardGetService.getBoardAll(teamId, member.getMemberId());
    }
}
