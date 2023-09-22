package com.moing.backend.domain.teamMember.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>, TeamMemberCustomRepository{

    TeamMember findTeamMemberByTeamAndMember(Team team, Member member);
}
