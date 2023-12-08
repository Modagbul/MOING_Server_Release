package com.moing.backend.domain.missionArchive.application.dto.req;

import lombok.*;

import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@Getter
public class MissionArchiveReq {

    private String status;
    @Size(min = 1, max = 1000)
    private String archive; //사진일 경우 파일명, 이외에는 text,link

    @Builder
    public MissionArchiveReq(String status, String archive) {
        this.status = status;
        this.archive = archive;
    }
}
