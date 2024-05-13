package com.moing.backend.domain.missionComment.domain.entity;

import com.moing.backend.domain.comment.domain.entity.Comment;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MissionComment extends Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_comment_id")
    private Long missionCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_archive_id")
    private MissionArchive missionArchive;

    /**
     * 연관관계 매핑
     */
    public void updateMissionArchive(MissionArchive missionArchive) {
        this.missionArchive=missionArchive;
    }

    public void updateTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    public void init(String content, boolean isLeader){
        this.content=content;
        this.isLeader=isLeader;
    }
}
