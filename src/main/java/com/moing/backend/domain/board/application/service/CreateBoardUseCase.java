package com.moing.backend.domain.board.application.service;

import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.CreateBoardResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardSaveService;
import com.moing.backend.domain.boardRead.application.service.CreateBoardReadUseCase;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.history.domain.service.AlarmHistorySaveService;
import com.moing.backend.domain.team.application.service.CheckLeaderUseCase;
import com.moing.backend.global.response.BaseServiceResponse;
import com.moing.backend.global.utils.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateBoardUseCase {


    private final BoardSaveService boardSaveService;
    private final CheckLeaderUseCase checkLeaderUseCase;
    private final CreateBoardReadUseCase createBoardReadUseCase;
    private final BaseService baseService;
    private final SendBoardAlarmUseCase sendBoardAlarmUseCase;

    /**
     * 게시글 생성
     */
    public CreateBoardResponse createBoard(String socialId, Long teamId, CreateBoardRequest createBoardRequest) {
        //1, 게시글 생성, 저장
        BaseServiceResponse data=baseService.getCommonData(socialId, teamId);
        boolean isLeader = checkLeaderUseCase.isTeamLeader(data.getMember(), data.getTeam()); //작성자 리더 여부
        Board board=boardSaveService.saveBoard(BoardMapper.toBoard(data.getTeamMember(), data.getTeam(), createBoardRequest, isLeader));

        //2. 읽음 처리 - 생성한 사람은 무조건 읽음
        createBoardReadUseCase.createBoardRead(data.getTeam(), data.getMember(), board);

        //3. 알림 보내기 - 공지인 경우
        sendBoardAlarmUseCase.sendNewUploadAlarm(data, board);

        return new CreateBoardResponse(board.getBoardId());
    }
}
