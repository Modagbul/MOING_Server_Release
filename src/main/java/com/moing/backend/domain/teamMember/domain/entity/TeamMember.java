package com.moing.backend.domain.teamMember.domain.entity;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="team_member_id")
    private Long teamMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isDeleted;     //소모임 삭제여부, 마이페이지에서는 상관 없이 모두 조회

    //==연관관계 메서드 ==//
    public void updateTeam(Team team) {
        this.team = team;
    }

    public void updateMember(Member member) {
        this.member = member;
        member.getTeamMembers().add(this);
    }

    public void withdrawTeam() {
        this.isDeleted = true;
    }
}
