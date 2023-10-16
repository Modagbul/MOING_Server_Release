package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MissionArchiveRes {

    private Long archiveId;
    private String archive;
    private String createdDate;
    private int hearts;
    private String status;
    private Long count;
    private String heartStatus;


}
