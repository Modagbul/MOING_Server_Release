package com.moing.backend.domain.missionArchive.application.dto.req;

import lombok.*;

@Getter
@NoArgsConstructor
public class MissionArchiveReq {

    private String status;
    private String archive; //사진일 경우 파일명, 이외에는 text,link

    @Builder
    public MissionArchiveReq(String status, String archive) {
        this.status = status;
        this.archive = archive;
    }
}
