package com.moing.backend.domain.missionArchive.application.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionArchiveHeartReq {
    private Long archiveId;
    private String heartStatus;
}
