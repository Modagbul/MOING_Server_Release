package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.teamMember.domain.repository.OrderCondition;

import java.util.List;
import java.util.Optional;

public interface MissionArchiveCustomRepository {
    public Optional<List<MissionArchive>> findSingleMissionArchivesByMemberId(Long memberId, List<Long> missionIds, String status, String archiveStatus, OrderCondition orderCondition);

    }
