package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.*;

import javax.annotation.Nullable;
import java.util.List;
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class MissionArchiveRes {

    private Long archiveId;
    private String archive;
    private String way;
    private String createdDate;
    private String status;
    private Long count;
    private String heartStatus;
    private Long hearts;
    private String contents;

    public void updateCount(Long count) {
        this.count = count;
    }


}
