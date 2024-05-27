package com.moing.backend.domain.report.application.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.REPORT_MESSAGE;
import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.REPORT_PHOTO;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveReportStrategy implements ReportStrategy {


    private final MissionArchiveQueryService missionArchiveQueryService;


    @Override
    public String processReport(Long targetId) {
        MissionArchive missionArchive = missionArchiveQueryService.findByMissionArchiveId(targetId);
        Mission mission = missionArchive.getMission();

        if (mission.getWay().equals(MissionWay.PHOTO) && missionArchive.getStatus().equals(MissionArchiveStatus.COMPLETE)) {
            missionArchive.updateArchive(MissionArchiveReq.builder()
                    .archive(REPORT_PHOTO.getMessage())
                    .status(missionArchive.getStatus().name())
                    .build());
        } else {
            missionArchive.updateArchive(MissionArchiveReq.builder()
                    .archive(REPORT_MESSAGE.getMessage())
                    .status(missionArchive.getStatus().name())
                    .build());

        }

        return getTargetMemberNickName(missionArchive);
    }

    private String getTargetMemberNickName(MissionArchive missionArchive){
        return missionArchive.getWriterNickName();
    }

}
