package com.moing.backend.domain.statistics.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.SqlResultSetMapping;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyStats {

    private long todayNewMembers;
    private long yesterdayNewMembers;
    private long todayNewTeams;
    private long yesterdayNewTeams;
    private long todayRepeatMission;
    private long yesterdayRepeatMission;
    private long todayOnceMission;
    private long yesterdayOnceMission;

}

