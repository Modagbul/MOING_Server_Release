package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long comments;

    public void updateCount(Long count) {
        this.count = count;
    }


}
