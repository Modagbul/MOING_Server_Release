package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.GetBoardDetailResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.boardRead.application.service.CreateBoardReadUserCase;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.global.response.BaseBoardServiceResponse;
import com.moing.backend.global.util.BaseBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetBoardUserCase {


    private final BaseBoardService baseBoardService;
    private final BoardMapper boardMapper;
    private final MemberGetService memberGetService;
    private final CreateBoardReadUserCase createBoardReadUserCase;
    private final BoardGetService boardGetService;


    /**
     * 게시글 상세 조회
     */
    public GetBoardDetailResponse getBoardDetail(String socialId, Long teamId, Long boardId) {
        BaseBoardServiceResponse data = baseBoardService.getCommonData(socialId, teamId, boardId);
        //읽음 처리
        createBoardReadUserCase.createBoardRead(data.getTeam(), data.getMember(), data.getBoard());
        return boardMapper.toBoardDetail(data.getBoard(), data.getTeamMember() == data.getBoard().getTeamMember());
    }

    /**
     * 게시글 전체 조회
     */
    public GetAllBoardResponse getAllBoard(String socialId, Long teamId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return boardGetService.getBoardAll(teamId, member.getMemberId());
    }
}
