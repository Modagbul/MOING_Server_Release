package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.application.service.SaveMultiAlarmHistoryUseCase;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveScheduleQueryService;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.moing.backend.global.config.fcm.constant.RemindMissionTitle.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionRemindAlarmUseCase {

    private final MissionArchiveScheduleQueryService missionArchiveScheduleQueryService;
    private final MissionQueryService missionQueryService;
    private final ApplicationEventPublisher eventPublisher;

    private final FcmService fcmService;
    private final SaveMultiAlarmHistoryUseCase saveMultiAlarmHistoryUseCase;

    String REMIND_NAME = "미션 리마인드";


    public Boolean sendRemindMissionAlarm() {

        Random random = new Random(System.currentTimeMillis());
        String title = getTitle(random.nextInt(4));
        String message = getMessage(random.nextInt(4));

        List<Member> remainMissionPeople = missionArchiveScheduleQueryService.getRemainMissionPeople();

        Optional<List<MemberIdAndToken>> memberIdAndTokens = mapToMemberAndToken(remainMissionPeople);
        Optional<List<MemberIdAndToken>> pushMemberIdAndToken = isPushMemberIdAndToken(remainMissionPeople);

//        eventPublisher.publishEvent(new MultiFcmEvent(title, message, pushMemberIdAndToken, memberIdAndTokens,
//                "",REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue()));

        if (pushMemberIdAndToken.isPresent() && !pushMemberIdAndToken.get().isEmpty()) {
            fcmService.sendMultipleDevices(new MultiRequest(pushMemberIdAndToken.get(), title,message,  "",REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue()));
        }
        if (memberIdAndTokens.isPresent() && !memberIdAndTokens.get().isEmpty()) {
            saveMultiAlarmHistoryUseCase.saveAlarmHistories(AlarmHistoryMapper.getMemberIds(memberIdAndTokens.get()),"",title,message,REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue());
        }
        return true;
    }


    public String getTitle(int num) {
        switch (num) {
            case 0:
                return REMIND_MISSION_TITLE1.getMessage();
            case 1:
                return REMIND_MISSION_TITLE2.getMessage();
            case 2:
                return REMIND_MISSION_TITLE3.getMessage();
            case 3:
                return REMIND_MISSION_TITLE4.getMessage();
        }
        return REMIND_MISSION_TITLE4.getMessage();

    }
    public String getMessage(int num) {
        switch (num) {
            case 0:
                return REMIND_MISSION_MESSAGE1.getMessage();
            case 1:
                return REMIND_MISSION_MESSAGE2.getMessage();
            case 2:
                return REMIND_MISSION_MESSAGE3.getMessage();
            case 3:
                return REMIND_MISSION_MESSAGE4.getMessage();
        }
        return REMIND_MISSION_MESSAGE4.getMessage();

    }


    public Boolean sendRepeatMissionRemindOnSunday() {

        List<Member> repeatMissionByStatus = missionQueryService.findRepeatMissionPeopleByStatus(MissionStatus.WAIT);

        Optional<List<MemberIdAndToken>> memberIdAndTokens = mapToMemberAndToken(repeatMissionByStatus);
        Optional<List<MemberIdAndToken>> pushMemberIdAndToken = isPushMemberIdAndToken(repeatMissionByStatus);


        eventPublisher.publishEvent(new MultiFcmEvent(REMIND_ON_SUNDAY_TITLE.getMessage(), REMIND_ON_SUNDAY_MESSAGE.getMessage(), pushMemberIdAndToken, memberIdAndTokens,
                "",REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue()));
        return true;


    }

    public Boolean sendRepeatMissionRemindOnMonday() {

        List<Member> repeatMissionByStatus = missionQueryService.findRepeatMissionPeopleByStatus(MissionStatus.ONGOING);

        Optional<List<MemberIdAndToken>> memberIdAndTokens = mapToMemberAndToken(repeatMissionByStatus);
        Optional<List<MemberIdAndToken>> pushMemberIdAndToken = isPushMemberIdAndToken(repeatMissionByStatus);

        eventPublisher.publishEvent(new MultiFcmEvent(REMIND_ON_MONDAY_TITLE.getMessage(), REMIND_ON_MONDAY_MESSAGE.getMessage(), pushMemberIdAndToken, memberIdAndTokens,
                "",REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue()));
        return true;


    }

    public Optional<List<MemberIdAndToken>> mapToMemberAndToken(List<Member> members) {
        return Optional.of(members.stream()
                .map(member -> MemberIdAndToken.builder()
                        .fcmToken(member.getFcmToken())
                        .memberId(member.getMemberId())
                        .build())
                .collect(Collectors.toList()));
    }
    public Optional<List<MemberIdAndToken>> isPushMemberIdAndToken(List<Member> members) {
        return Optional.of(members.stream()
                .map(member -> {
                    if (member.isRemindPush() && !member.isSignOut()) {
                        return MemberIdAndToken.builder()
                                .fcmToken(member.getFcmToken())
                                .memberId(member.getMemberId())
                                .build();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }




}
