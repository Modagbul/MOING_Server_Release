package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>, TeamMemberCustomRepository{
    Optional<TeamMember> findTeamMemberByTeamAndMember(Team team, Member member);

    @Modifying
    @Query("update TeamMember t set t.isDeleted = true where t.team.teamId = :teamId")
    void deleteTeamMembers(Long teamId);

}
