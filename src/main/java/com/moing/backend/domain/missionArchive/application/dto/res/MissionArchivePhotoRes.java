package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionArchivePhotoRes {
    Long teamId;
    List<String> photo;
}
