package com.moing.backend.domain.member.application.service;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.application.service.SaveMultiAlarmHistoryUseCase;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.entity.PagePath;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.service.SendMissionStartAlarmUseCase;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.MultiMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.moing.backend.global.config.fcm.constant.RemindMissionTitle.REMIND_ON_MONDAY_MESSAGE;
import static com.moing.backend.global.config.fcm.constant.RemindMissionTitle.REMIND_ON_MONDAY_TITLE;
@Service
@Transactional
@RequiredArgsConstructor
public class UpdateRemindAlarmUseCase {

    private final MemberGetService memberGetService;
    private final SaveMultiAlarmHistoryUseCase saveMultiAlarmHistoryUseCase;
    private final MultiMessageSender multiMessageSender;

    String REMIND_NAME = "미션 리마인드";


    public Boolean sendUpdateAppPushAlarm() {

        String title = "MOING 업데이트 소식";
        String message = "이제 사진 인증에 설명을 추가할 수 있어요. 지금 업데이트하고 다른 소식도 확인해보세요!";

        List<Member> allMemberOfPushAlarm = memberGetService.getAllMemberOfPushAlarm();

        // TODO : 500명 단위 배치처리

        // FCM 제한으로 500명씩 나눠서 보내기 (0~498)
        List<Member> firstMembers = allMemberOfPushAlarm.subList(0, 498);

        Optional<List<MemberIdAndToken>> memberIdAndTokens = mapToMemberAndToken(firstMembers);
        Optional<List<MemberIdAndToken>> pushMemberIdAndToken = isPushMemberIdAndToken(firstMembers);

        if (pushMemberIdAndToken.isPresent() && !pushMemberIdAndToken.get().isEmpty()) {
            multiMessageSender.send(new MultiRequest(pushMemberIdAndToken.get(), title, message, "", REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue()));
        }
        if (memberIdAndTokens.isPresent() && !memberIdAndTokens.get().isEmpty()) {
            saveMultiAlarmHistoryUseCase.saveAlarmHistories(AlarmHistoryMapper.getMemberIds(memberIdAndTokens.get()), "", title, message, REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue());
        }

        // FCM 제한으로 500명씩 나눠서 보내기 (498~)
        List<Member> remainMembers = allMemberOfPushAlarm.subList(499, allMemberOfPushAlarm.size());

        Optional<List<MemberIdAndToken>> memberIdAndTokens2 = mapToMemberAndToken(remainMembers);
        Optional<List<MemberIdAndToken>> pushMemberIdAndToken2 = isPushMemberIdAndToken(remainMembers);

        if (pushMemberIdAndToken2.isPresent() && !pushMemberIdAndToken2.get().isEmpty()) {
            multiMessageSender.send(new MultiRequest(pushMemberIdAndToken2.get(), title, message, "", REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue()));
        }
        if (memberIdAndTokens2.isPresent() && !memberIdAndTokens2.get().isEmpty()) {
            saveMultiAlarmHistoryUseCase.saveAlarmHistories(AlarmHistoryMapper.getMemberIds(memberIdAndTokens2.get()), "", title, message, REMIND_NAME, AlarmType.REMIND, PagePath.MISSION_ALL_PTAH.getValue());
        }
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
