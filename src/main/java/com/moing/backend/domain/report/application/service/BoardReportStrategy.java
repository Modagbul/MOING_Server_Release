package com.moing.backend.domain.report.application.service;

import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.REPORT_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardReportStrategy implements ReportStrategy {

    private final BoardGetService boardGetService;

    @Override
    public String processReport(Long targetId) {
        Board board = boardGetService.getBoard(targetId);
        board.updateBoard(UpdateBoardRequest.builder()
                .title(REPORT_MESSAGE.getMessage())
                .content(REPORT_MESSAGE.getMessage())
                .isNotice(board.isNotice())
                .build());
        return getTargetMemberNickName(board);
    }

    private String getTargetMemberNickName(Board board){
        return board.getWriterNickName();
    }
}