package com.moing.backend.domain.mypage.domain.repository;

import com.moing.backend.domain.mypage.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
