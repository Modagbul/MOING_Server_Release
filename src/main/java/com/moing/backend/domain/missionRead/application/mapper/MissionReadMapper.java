package com.moing.backend.domain.missionRead.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.missionRead.domain.entity.MissionRead;
import com.moing.backend.domain.team.domain.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class MissionReadMapper {
    public static MissionRead toMissionRead(Team team, Member member){
        MissionRead missionRead=new MissionRead();
        missionRead.updateTeam(team);
        missionRead.updateMember(member);
        return missionRead;
    }
}
