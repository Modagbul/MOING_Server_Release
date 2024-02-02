package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionRecommendRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import com.moing.backend.domain.mission.exception.NoAccessCreateMission;
import com.moing.backend.domain.mission.exception.NoMoreCreateMission;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.global.response.BaseServiceResponse;
import com.moing.backend.global.utils.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCreateUseCase {

    private final MissionSaveService missionSaveService;
    private final MissionQueryService missionQueryService;
    private final SendMissionCreateAlarmUseCase sendMissionCreateAlarmUseCase;

    private final BaseService baseService;

    public MissionCreateRes createMission(String socialId, Long teamId, MissionReq missionReq) {

        BaseServiceResponse commonData = baseService.getCommonData(socialId, teamId);
        Member member = commonData.getMember();
        Team team = commonData.getTeam();

        Mission mission = MissionMapper.mapToMission(missionReq, member, MissionStatus.WAIT);
        mission.setTeam(team);

        if (mission.getType().equals(MissionType.REPEAT)) {
            // 반복 미션 생성일 경우 소모임장만 가능
            if (!(member.getMemberId().equals(team.getLeaderId()))) {
                throw new NoAccessCreateMission();
            // 반복미션은 최대 2개 생성 가능
            } else if (missionQueryService.isAbleCreateRepeatMission(team.getTeamId())) {
                throw new NoAccessCreateMission();
            }
            // 반복미션 유예 해제
            mission.updateStatus(MissionStatus.ONGOING);

        }
        // 2. 미션 저장
        missionSaveService.save(mission);

        // 3. 알림 보내기 - 미션 생성
        sendMissionCreateAlarmUseCase.sendNewMissionUploadAlarm(member, mission);

        return MissionMapper.mapToMissionCreateRes(mission);
    }


    public Boolean getIsLeader(String socialId, Long teamId) {
        BaseServiceResponse commonData = baseService.getCommonData(socialId, teamId);
        Member member = commonData.getMember();
        Team team = commonData.getTeam();

        return member.getMemberId().equals(team.getLeaderId());

    }

}
