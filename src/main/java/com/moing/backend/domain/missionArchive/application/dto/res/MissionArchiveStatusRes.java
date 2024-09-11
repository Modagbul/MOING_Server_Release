package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class MissionArchiveStatusRes {
    private String total;
    private String done;
}
