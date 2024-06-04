package com.moing.backend.domain.missionArchive.domain.entity;


import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MissionArchive extends BaseTimeEntity { // 1회 미션을 저장 하는 저장소

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "missionArchive_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(value = EnumType.STRING)
    private MissionArchiveStatus status;

    @Column(nullable = false, columnDefinition="TEXT", length = 4000)
    private String archive; //링크, 글, 사진 뭐든 가능

    private Long count; // 횟수

    @Column(nullable = true, columnDefinition="TEXT", length = 1000)
    private String contents;

    @OneToMany(mappedBy = "missionArchive", cascade = CascadeType.REMOVE)
    private List<MissionHeart> heartList = new ArrayList<>();

    //반정규화
    private Long commentNum;

    public void updateArchive(MissionArchiveReq missionArchiveReq) {
        this.archive = missionArchiveReq.getArchive();
        this.status = MissionArchiveStatus.valueOf(missionArchiveReq.getStatus());
    }

    public void updateArchive(String archive) {
        this.archive = archive;
    }

    public void updateCount(Long count) {
        this.count = count;
    }

    public void incrComNum() {
        this.commentNum++;
    }

    public void decrComNum() {
        this.commentNum--;
    }


    public String getWriterNickName(){
        return member.getNickName();
    }
}
