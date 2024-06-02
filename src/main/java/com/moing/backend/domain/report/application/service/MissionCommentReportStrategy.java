package com.moing.backend.domain.report.application.service;

import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.missionComment.domain.service.MissionCommentGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.REPORT_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCommentReportStrategy implements ReportStrategy {


    private final MissionCommentGetService missionCommentGetService;


    @Override
    public String processReport(Long targetId) {
        MissionComment missionComment=missionCommentGetService.getComment(targetId);
        missionComment.updateContent(REPORT_MESSAGE.getMessage());

        return getTargetMemberNickName(missionComment);
    }

    private String getTargetMemberNickName(MissionComment missionComment){
        return missionComment.getWriterNickName();
    }
}
