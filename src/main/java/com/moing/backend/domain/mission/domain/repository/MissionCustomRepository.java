package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionCustomRepository {
    Long findMissionsCountByTeam(Long teamId);

    Optional<List<GatherSingleMissionRes>> findSingleMissionByMemberId(Long memberId, List<Long> teams);

    Optional<List<GatherRepeatMissionRes>> findRepeatMissionByMemberId(Long memberId, List<Long> teams);

    Optional<List<Mission>> findMissionByDueTo();

    Optional<List<Long>> findOngoingRepeatMissions();

    Optional<List<Mission>> findRepeatMissionByStatus(MissionStatus missionStatus);

    Optional<List<Member>> findRepeatMissionPeopleByStatus(MissionStatus missionStatus);

    boolean findRepeatMissionsByTeamId(Long teamId);

    Optional<MissionReadRes> findByIds(Long memberId, Long missionId);

    Long getTodayOnceMissions();

    Long getYesterdayOnceMissions();

    Long getTodayRepeatMissions();

    Long getYesterdayRepeatMissions();
}