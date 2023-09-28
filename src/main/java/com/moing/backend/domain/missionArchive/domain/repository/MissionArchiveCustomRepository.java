package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.teamMember.domain.repository.OrderCondition;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface MissionArchiveCustomRepository {
    Optional<List<MissionArchive>> findSingleMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status, MissionArchiveStatus archiveStatus, OrderCondition orderCondition);
    Optional<List<MissionArchive>> findOthersArchives(Long memberId, Long missionId) ;

    Optional<List<MissionArchive>> findAllMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus missionStatus);


    }