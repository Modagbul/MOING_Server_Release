package com.moing.backend.domain.missionArchive.domain.entity;


import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.global.entity.BaseTimeEntity;
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

    @Column(nullable = false)
    private String archive; //링크, 글, 사진 뭐든 가능

    private int hearts;

    private Long count;


    public void updateArchive(MissionArchiveReq missionArchiveReq) {
        this.archive = missionArchiveReq.getArchive();
        this.status = MissionArchiveStatus.valueOf(missionArchiveReq.getStatus());
    }
    public int updateHearts(Boolean status) {
        if(status)
            return this.hearts += 1;
        else
            return this.hearts -= 1;
    }

    public void updateCount(Long count) {
        this.count = count;
    }


}
