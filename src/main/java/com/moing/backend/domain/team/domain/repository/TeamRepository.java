package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamCustomRepository {
}
