package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonalArchiveRes {

    private Long archiveId;
    private String nickname;
    private String profileImg;

    private String archive;
    private String createdDate;

    private String heartStatus;
    private int hearts;

    private String status;
    private Long count;

}
