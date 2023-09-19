package com.moing.backend.domain.team.domain.entity;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.constant.Category;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Category category;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private String profileImgUrl;

    @Column(nullable = false, length = 300)
    private String introduction;

    @Column(nullable = false, length = 100)
    private String promise;

    @Column(nullable = false)
    private Long leaderId;

    private String invitationCode;

    @Column(nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    private LocalDateTime approvalTime;

    private boolean isDeleted;

    private LocalDateTime deletionTime;

    @OneToMany(mappedBy = "team")
    List<Mission> missions = new ArrayList<>();

    public static Team createTeam(CreateTeamRequest createTeamRequest, Member member) {
        return Team.builder()
                .category(Enum.valueOf(Category.class, createTeamRequest.getCategory()))
                .name(createTeamRequest.getName())
                .introduction(createTeamRequest.getIntroduction())
                .promise(createTeamRequest.getPromise())
                .profileImgUrl(createTeamRequest.getProfileImgUrl())
                .approvalStatus(ApprovalStatus.NO_CONFIRMATION)
                .leaderId(member.getMemberId())
                .build();
    }

    public void approveTeam() {
        this.approvalStatus = ApprovalStatus.APPROVAL;
        this.approvalTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).withNano(0);
    }

    public void rejectTeam() {
        this.approvalStatus = ApprovalStatus.REJECTION;
    }

    public void updateTeam(String name, String introduction, String profileImgUrl) {
        this.name = name;
        this.introduction = introduction;
        this.profileImgUrl = profileImgUrl;
    }

    public void deleteTeam() {
        this.isDeleted = true;
        this.deletionTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).withNano(0);
    }
}
