package com.moing.backend.domain.missionHeart.domain.entity;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionHeart.domain.constant.MissionHeartStatus;
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
public class MissionHeart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "missionHeart_id")
    private Long id;

    private Long pushMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionArchiveId")
    private MissionArchive missionArchive;

    @Enumerated(EnumType.STRING)
    private MissionHeartStatus heartStatus;

    public void updateHeartStatus(MissionHeartStatus heartStatus) {
        this.heartStatus = heartStatus;
    }

    public void changeByHeart(MissionArchive missionArchive) {
        this.missionArchive = missionArchive;
        missionArchive.getHeartList().add(this);
    }


}
