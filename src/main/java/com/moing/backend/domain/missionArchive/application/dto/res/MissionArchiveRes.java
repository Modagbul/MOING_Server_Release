package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MissionArchiveRes {

    private String archive;

    private String createdDate;

    private List<PersonalArchive> donePersonalArchives;

    public void setPersonalArchives(List<PersonalArchive> done) {
        this.donePersonalArchives = done;
    }
}
