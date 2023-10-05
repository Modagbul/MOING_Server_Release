package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.CreateBoardResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardSaveService;
import com.moing.backend.domain.boardRead.application.mapper.BoardReadMapper;
import com.moing.backend.domain.boardRead.application.service.CreateBoardReadUserCase;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.boardRead.domain.service.BoardReadSaveService;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.service.CheckLeaderUserCase;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.BaseServiceResponse;
import com.moing.backend.global.util.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateBoardUserCase {


    private final BoardSaveService boardSaveService;
    private final CheckLeaderUserCase checkLeaderUserCase;
    private final BoardMapper boardMapper;
    private final CreateBoardReadUserCase createBoardReadUserCase;
    private final BaseService baseService;

    /**
     * 게시글 생성
     */
    public CreateBoardResponse createBoard(String socialId, Long teamId, CreateBoardRequest createBoardRequest) {
        //1, 게시글 생성, 저장
        BaseServiceResponse data=baseService.getCommonData(socialId, teamId);
        boolean isLeader = checkLeaderUserCase.isTeamLeader(data.getMember(), data.getTeam()); //작성자 리더 여부
        Board board=boardSaveService.saveBoard(boardMapper.toBoard(data.getMember(), data.getTeamMember(), data.getTeam(), createBoardRequest, isLeader));

        //2. 읽음 처리 - 생성한 사람은 무조건 읽음
        createBoardReadUserCase.createBoardRead(data.getTeam(), data.getMember(), board);

        //3. 알림 보내기 - 공지인 경우
        return new CreateBoardResponse(board.getBoardId());
    }
}
