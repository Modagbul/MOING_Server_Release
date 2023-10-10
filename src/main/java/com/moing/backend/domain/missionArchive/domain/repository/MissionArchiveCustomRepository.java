package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MissionArchiveCustomRepository {
    Optional<List<MissionArchive>> findSingleMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status, MissionArchiveStatus archiveStatus, OrderCondition orderCondition);
    Optional<List<MissionArchive>> findOthersArchives(Long memberId, Long missionId) ;

    Optional<List<MissionArchive>> findAllMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus missionStatus);

    Optional<Long> findDonePeopleByMissionId(Long missionId);
    Optional<Long> findMyDoneCountByMissionId(Long missionId,Long memberId);

    Optional<List<RepeatMissionBoardRes>> findRepeatMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status);

    Optional<List<FinishMissionBoardRes>> findFinishMissionsByStatus(Long memberId, Long teamId);

    }
