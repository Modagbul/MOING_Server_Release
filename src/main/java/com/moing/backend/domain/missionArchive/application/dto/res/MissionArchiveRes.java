package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.List;

@Builder
@Getter
@Setter
public class MissionArchiveRes {

    private Long archiveId;
    private String archive;
    private String createdDate;
    private String status;
    private Long count;
    private String heartStatus;
    private Long hearts;

    public void updateHeartStatus(boolean status) {
        if (status) {
            this.heartStatus = "True";
        }else{
            this.heartStatus = "False";
        }
    }


}
