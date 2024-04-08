package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveDeleteService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.exception.NoAccessMissionArchiveException;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveDeleteUseCase {

    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MissionArchiveDeleteService missionArchiveDeleteService;
    private final MissionQueryService missionQueryService;

    private final MemberGetService memberGetService;

    private final UpdateUtils updateUtils;



    public Long deleteArchive(String userSocialId, Long missionId,Long count) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Long memberId = member.getMemberId();
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        MissionArchive deleteArchive = missionArchiveQueryService.findOneMyArchive(memberId, missionId,count).get(0);

        LocalDateTime createdDate = deleteArchive.getCreatedDate();
        LocalDateTime today = LocalDateTime.now();

        // 반복미션이면서 오늘 이전에 한 인증은 인증 취소할 수 없도록
        if (mission.getType().equals(MissionType.REPEAT) && createdDate.toLocalDate().isBefore(today.toLocalDate())) {
            throw new NoAccessMissionArchiveException();
        }

        if (mission.getWay().equals(MissionWay.PHOTO)) {
            String archive = deleteArchive.getArchive();
            updateUtils.deleteImgUrl(archive);
        }

        missionArchiveDeleteService.deleteMissionArchive(deleteArchive);

        return deleteArchive.getId();

    }
}
