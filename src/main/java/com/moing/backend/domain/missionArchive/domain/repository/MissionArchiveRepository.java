package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionArchiveRepository extends JpaRepository<MissionArchive, Long>,MissionArchiveCustomRepository {

    @Query("select m from MissionArchive as m where m.mission.id = :missionId and m.member.memberId =:memberId order by m.createdDate")
    Optional<List<MissionArchive>> findArchivesByMissionIdAndMemberId(@Param("memberId") Long memberId, @Param("missionId")Long missionId);

    }
