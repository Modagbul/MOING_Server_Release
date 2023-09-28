package com.moing.backend.domain.missionArchive.application.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MissionArchiveReq {

    private String status;
    private String archive; //사진일 경우 파일명, 이외에는 text,link
}
