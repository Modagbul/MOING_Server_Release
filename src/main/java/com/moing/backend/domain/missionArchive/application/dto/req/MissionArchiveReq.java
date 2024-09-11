package com.moing.backend.domain.missionArchive.application.dto.req;

import lombok.*;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@Getter
public class MissionArchiveReq {

    private String status;

    @Size(min = 1, max = 1000)
    private String archive; //사진일 경우 파일명, 이외에는 text,link

    private String contents;

    @Builder
    public MissionArchiveReq(String status, String archive, String contents) {
        this.status = status;
        this.archive = archive;
        this.contents = contents;
    }
}
