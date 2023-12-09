package com.moing.backend.domain.team.domain.entity;

import com.moing.backend.domain.mission.domain.entity.Mission;
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

    private Integer numOfMember; //반정규화
    private Integer levelOfFire;

    @OneToMany(mappedBy = "team")
    List<Mission> missions = new ArrayList<>();

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
        this.isDeleted=true;
        //TODO 테스트 용으로 현재 시간이 아닌 4일 전으로
//        this.deletionTime = LocalDateTime.now().withNano(0);
        this.deletionTime=LocalDateTime.now().minusDays(4).withNano(0);
    }

    public void addTeamMember(){
        numOfMember++;
    }

    public void deleteTeamMember(){
        numOfMember--;
    }

    public void updateLevelOfFire() {
        levelOfFire++;
    }
}
