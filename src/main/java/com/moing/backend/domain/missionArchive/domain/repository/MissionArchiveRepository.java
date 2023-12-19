package com.moing.backend.domain.missionArchive.domain.repository;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.querydsl.core.Tuple;
import feign.Param;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MissionArchiveRepository extends JpaRepository<MissionArchive, Long>,MissionArchiveCustomRepository {

    @Query("select m from MissionArchive as m where m.member =:memberId" )
    Optional<List<MissionArchive>> findByMemberId(@Param("memberId") Long memberId);

    @Query("select m from MissionArchive as m where m.mission.id = :missionId and m.member.memberId =:memberId order by m.createdDate")
    Optional<List<MissionArchive>> findArchivesByMissionIdAndMemberId(@Param("memberId") Long memberId, @Param("missionId")Long missionId);

    @Query("select m from MissionArchive as m where m.mission.id = :missionId and m.member.memberId =:memberId")
    Optional<MissionArchive> findByMissionIdAndMemberId(@Param("memberId") Long memberId, @Param("missionId")Long missionId);


   @Query("select m from MissionArchive as m where m.mission.id IN :missionIds and m.member.memberId =:memberId")
   Optional<List<MissionArchive>> findRepeatMissionArchivesByMission (@Param("memberId") Long memberId, @Param("missionIds") List <Long> missionIds);


//   @Query(value = "SELECT distinct tmSub.fcm_token, tmSub.member_id" +
//           "FROM ( " +
//           "         SELECT  distinct COALESCE(tm.member_id, 0) AS member_id, t.team_id, me.fcm_token " +
//           "         FROM mission m " +
//           "                  LEFT JOIN team t ON m.team_id = t.team_id " +
//           "                  LEFT JOIN team_member tm ON t.team_id = tm.team_id AND tm.is_deleted = 'False' " +
//           "                  LEFT JOIN member me on tm.member_id = me.member_id " +
//           "     ) tmSub " +
//           "         LEFT JOIN mission m ON NOT (m.status = 'END' OR m.status = 'SUCCESS') and m.team_id = tmSub.team_id " +
//           "         LEFT JOIN mission_archive ms ON m.mission_id = ms.mission_id and ms.member_id = tmSub.member_id " +
//           "GROUP BY tmSub.member_id, m.mission_id, m.number " +
//           "having COUNT(ms.mission_archive_id) < m.number", nativeQuery = true)
//    Optional<List<MemberIdAndToken>> findHavingRemainMissions();


    @Query(value = "SELECT distinct COALESCE(tmSub.fcm_token,'undef') as fcmToken, tmSub.member_id as memberId " +
            "FROM (SELECT distinct COALESCE(tm.member_id, 0) AS member_id, t.team_id, me.fcm_token " +
            "FROM mission m " +
            "LEFT JOIN team t ON m.team_id = t.team_id " +
            "LEFT JOIN team_member tm ON t.team_id = tm.team_id AND tm.is_deleted = 'False' " +
            "LEFT JOIN member me on tm.member_id = me.member_id) tmSub " +
            "LEFT JOIN mission m ON NOT (m.status = 'END' OR m.status = 'SUCCESS') and m.team_id = tmSub.team_id " +
            "LEFT JOIN mission_archive ms ON m.mission_id = ms.mission_id and ms.member_id = tmSub.member_id " +
            "GROUP BY tmSub.member_id, m.mission_id, m.number " +
            "HAVING COUNT(ms.mission_archive_id) < m.number", nativeQuery = true
    )
    Optional<List<Map<String, Long>>> findHavingRemainMissions();


//    @Query(value = "SELECT distinct tmSub.fcm_token as fcmToken, tmSub.member_id as memberId " +
//            "FROM (SELECT distinct COALESCE(tm.member_id, 0) AS member_id, t.team_id, me.fcm_token " +
//            "FROM mission m " +
//            "LEFT JOIN team t ON m.team_id = t.team_id " +
//            "LEFT JOIN team_member tm ON t.team_id = tm.team_id AND tm.is_deleted = 'False' " +
//            "LEFT JOIN member me on tm.member_id = me.member_id) tmSub " +
//            "LEFT JOIN mission m ON NOT (m.status = 'END' OR m.status = 'SUCCESS') and m.team_id = tmSub.team_id " +
//            "LEFT JOIN mission_archive ms ON m.mission_id = ms.mission_id and ms.member_id = tmSub.member_id " +
//            "GROUP BY tmSub.member_id, m.mission_id, m.number " +
//            "HAVING COUNT(ms.mission_archive_id) < m.number", nativeQuery = true
//    )
//    Optional<List<MemberIdAndToken>> findHavingRemainMissions();




    }
