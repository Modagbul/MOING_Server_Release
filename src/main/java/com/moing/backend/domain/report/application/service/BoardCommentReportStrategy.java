package com.moing.backend.domain.report.application.service;

import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.REPORT_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardCommentReportStrategy implements ReportStrategy {


    private final BoardCommentGetService boardCommentGetService;


    @Override
    public String processReport(Long targetId) {
        BoardComment boardComment = boardCommentGetService.getComment(targetId);
        boardComment.updateContent(REPORT_MESSAGE.getMessage());
        return getTargetMemberNickName(boardComment);
    }

    private String getTargetMemberNickName(BoardComment boardComment){
        return boardComment.getWriterNickName();
    }
}
