package com.moing.backend.domain.history.domain.repository;

import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long>, AlarmHistoryCustomRepository {

    Optional<AlarmHistory> findAlarmHistoryByIdAndReceiverId(Long id, Long receiverId);
}
