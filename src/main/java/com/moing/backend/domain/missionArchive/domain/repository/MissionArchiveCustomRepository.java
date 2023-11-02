package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchivePhotoRes;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MissionArchiveCustomRepository {
    Optional<List<SingleMissionBoardRes>> findSingleMissionInComplete(Long memberId, Long teamId, MissionStatus status,OrderCondition orderCondition);
    Optional<List<SingleMissionBoardRes>> findSingleMissionComplete(Long memberId, Long teamId, MissionStatus status,OrderCondition orderCondition);
    Optional<List<MissionArchive>> findMyArchives(Long memberId,Long missionId);

    Optional<List<MissionArchive>> findOthersArchives(Long memberId, Long missionId) ;

    Optional<Long> findDonePeopleByMissionId(Long missionId);
    Optional<Long> findMyDoneCountByMissionId(Long missionId,Long memberId);

    Optional<List<RepeatMissionBoardRes>> findRepeatMissionArchivesByMemberId(Long memberId, Long teamId, MissionStatus status);

    Optional<List<FinishMissionBoardRes>> findFinishMissionsByStatus(Long memberId, Long teamId);

    Optional<List<MissionArchivePhotoRes>> findTop5ArchivesByTeam(List<Long> teamIds);


    }
