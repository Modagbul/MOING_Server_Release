package com.moing.backend.domain.fire.domain.repository;

import com.moing.backend.domain.fire.domain.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireRepository extends JpaRepository<Fire,Long>,FireCustomRepository {
}
