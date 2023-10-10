package com.moing.backend.domain.fire.domain.repository;


import com.moing.backend.domain.fire.domain.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FireCustomRepository extends JpaRepository<Fire, Long> {

    Optional<Fire> findMyRecentFire(Long memberId);

}
