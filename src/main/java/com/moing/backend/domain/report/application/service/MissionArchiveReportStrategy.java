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

        if (isCompletePhotoMission(missionArchive)) {
            missionArchive.updateArchive(REPORT_PHOTO.getMessage());
        } else {
            missionArchive.updateArchive(REPORT_MESSAGE.getMessage());
        }

        return getTargetMemberNickName(missionArchive);
    }

    private String getTargetMemberNickName(MissionArchive missionArchive){
        return missionArchive.getWriterNickName();
    }

    private Boolean isCompletePhotoMission(MissionArchive archive) {
        return archive.getMission().getWay().equals(MissionWay.PHOTO) && archive.getStatus().equals(MissionArchiveStatus.COMPLETE);
    }
}
