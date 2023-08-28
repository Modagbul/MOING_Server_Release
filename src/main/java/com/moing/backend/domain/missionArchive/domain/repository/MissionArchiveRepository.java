package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MissionArchiveRepository extends JpaRepository<MissionArchive, Long> {

    @Query("select m from MissionArchive as m where m.member =: memberId")
    public Optional<MissionArchive> findByMemberId(Long memberId);

    @Query("select m from MissionArchive as m where m.mission.id =: missionId and m.member.memberId =: memberId")
    public Optional<MissionArchive> findByMissionIdAndMemberId(Long memberId, Long missionId);

}
