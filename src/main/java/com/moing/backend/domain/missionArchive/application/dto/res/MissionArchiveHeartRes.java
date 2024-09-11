package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionArchiveHeartRes {
    private Long archiveId;
    private String heartStatus;
    private int hearts;
}
