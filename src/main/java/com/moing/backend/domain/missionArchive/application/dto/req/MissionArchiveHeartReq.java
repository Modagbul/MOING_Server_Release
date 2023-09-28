package com.moing.backend.domain.missionArchive.application.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionArchiveHeartReq {
    private Long archiveId;
    private String heartStatus;
}
