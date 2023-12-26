package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class PersonalArchiveRes {

    private Long archiveId;
    private String nickname;
    private String profileImg;

    private String archive;
    private String createdDate;
    private String way;

    private String heartStatus;
    private int hearts;

    private String status;
    private Long count;

    private Long makerId;


}
