package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import feign.Param;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MissionArchiveRepository extends JpaRepository<MissionArchive, Long>,MissionArchiveCustomRepository {

    @Query("select m from MissionArchive as m where m.member =:memberId" )
    Optional<List<MissionArchive>> findByMemberId(@Param("memberId") Long memberId);

    @Query("select m from MissionArchive as m where m.mission.id = :missionId and m.member.memberId =:memberId")
    Optional<List<MissionArchive>> findArchivesByMissionIdAndMemberId(@Param("memberId") Long memberId, @Param("missionId")Long missionId);

    @Query("select m from MissionArchive as m where m.mission.id = :missionId and m.member.memberId =:memberId")
    Optional<MissionArchive> findByMissionIdAndMemberId(@Param("memberId") Long memberId, @Param("missionId")Long missionId);


   @Query("select m from MissionArchive as m where m.mission.id IN :missionIds and m.member.memberId =:memberId")
   Optional<List<MissionArchive>> findRepeatMissionArchivesByMission (@Param("memberId") Long memberId, @Param("missionIds") List <Long> missionIds);

}
