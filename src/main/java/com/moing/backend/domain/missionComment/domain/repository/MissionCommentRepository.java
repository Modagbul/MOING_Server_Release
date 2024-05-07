package com.moing.backend.domain.missionComment.domain.repository;

import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionCommentRepository extends JpaRepository<MissionComment, Long>, MissionCommentCustomRepository {

    Optional<MissionComment> findMissionCommentByMissionCommentId(Long missionCommentId);

}
