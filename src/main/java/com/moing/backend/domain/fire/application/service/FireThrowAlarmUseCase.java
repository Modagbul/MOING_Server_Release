package com.moing.backend.domain.fire.application.service;

import com.moing.backend.domain.fire.application.dto.req.FireThrowReq;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.config.fcm.dto.event.SingleFcmEvent;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

import static com.moing.backend.domain.history.domain.entity.PagePath.MISSION_PATH;
import static com.moing.backend.global.config.fcm.constant.FireThrowMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FireThrowAlarmUseCase {

    private final ApplicationEventPublisher eventPublisher;

    public void sendFireThrowAlarm(Member throwMember, Member receiveMember, Team team, Mission mission, FireThrowReq fireThrowReq) {

        int randomNum = new Random(System.currentTimeMillis()).nextInt(2);

        String title = fireThrowReq != null ? NEW_FIRE_THROW_TITLE_WITH_COMMENT.to(throwMember.getNickName())
                : getRandomTitle(throwMember.getNickName(), receiveMember.getNickName(), randomNum);
        String message = fireThrowReq != null ? fireThrowReq.getMessage()
                : getRandomMessage(throwMember.getNickName(), receiveMember.getNickName(), randomNum);
        String idInfo = createIdInfo(mission.getType() == MissionType.REPEAT, mission.getTeam().getTeamId(), mission.getId());

        eventPublisher.publishEvent(new SingleFcmEvent(receiveMember, title, message, idInfo, team.getName(), AlarmType.FIRE, MISSION_PATH.getValue(), receiveMember.isFirePush()));
    }

    public String getRandomMessage(String pusher, String receiver, int num) {

        switch (num) {
            case 0:
                return NEW_FIRE_THROW_MESSAGE1.fromTo(pusher, receiver);
            case 1: return NEW_FIRE_THROW_MESSAGE2.toFrom(receiver, pusher);

        }
        return NEW_FIRE_THROW_MESSAGE1.fromTo(pusher, receiver);
    }

    public String getRandomTitle(String pusher, String receiver, int num) {

        switch (num) {
            case 0:
                return NEW_FIRE_THROW_TITLE1.getMessage();
            case 1:
                return NEW_FIRE_THROW_TITLE2.getMessage();
        }
        return NEW_FIRE_THROW_TITLE1.getMessage();
    }

    private String createIdInfo(boolean isRepeated, Long teamId, Long missionId) {
        JSONObject jo = new JSONObject();
        jo.put("isRepeated", isRepeated);
        jo.put("teamId", teamId);
        jo.put("missionId", missionId);
        return jo.toJSONString();
    }


}
