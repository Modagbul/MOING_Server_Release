package com.moing.backend.domain.missionArchive.application.dto.res;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class MyMissionArchiveRes {
    private String today;
    private List<MissionArchiveRes> archives;

    public void updateArchives(List<MissionArchiveRes> archives) {
        this.archives = archives;
    }

    public void updateTodayStatus(Boolean bo) {
        if (bo) {
            this.today = "True";
        } else {
            this.today = "False";
        }
    }

}
