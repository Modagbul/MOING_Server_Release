package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>, TeamMemberCustomRepository{
}
