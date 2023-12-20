package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.*;

@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class MyArchiveStatus {
    private boolean end;
    private String status;
}
