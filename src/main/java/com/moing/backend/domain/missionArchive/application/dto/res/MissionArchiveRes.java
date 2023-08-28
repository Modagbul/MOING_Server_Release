package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MissionArchiveRes {

    private String title;
    private String status;
    private String dueTo;

    private int totalPerson;
    private int donePerson;


    private String rule;
    private String content;

    private String archive;

    private String createdDate;






}
