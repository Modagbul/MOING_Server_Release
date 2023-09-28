package com.moing.backend.domain.score.application.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;

public class ScoreUpdateUseCase {

    public int singleMissionDone(Mission mission) {

        if (mission.getStatus() == MissionStatus.END) {


        }

        return 3;
    }
}
