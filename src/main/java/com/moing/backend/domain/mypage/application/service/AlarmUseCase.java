package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.response.GetAlarmResponse;
import com.moing.backend.domain.mypage.exception.AlarmInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmUseCase {

    private final MemberGetService memberGetService;

    @Transactional(readOnly = true)
    public GetAlarmResponse getAlarm(String socialId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return new GetAlarmResponse(member.isNewUploadPush(),member.isRemindPush(), member.isFirePush(), member.isCommentPush());
    }

    @Transactional
    public GetAlarmResponse updateAlarm(String socialId, String type, String status) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        boolean push = "on".equals(status);

        switch (type) {
            case "all":
                member.updateAllPush(push);
                break;
            case "isNewUploadPush":
                member.updateNewUploadPush(push);
                break;
            case "isRemindPush":
                member.updateRemindPush(push);
                break;
            case "isFirePush":
                member.updateFirePush(push);
                break;
            case "isCommentPush":
                member.updateCommentPush(push);
            default:
                throw new AlarmInvalidException();
        }
        return new GetAlarmResponse(member.isNewUploadPush(),member.isRemindPush(), member.isFirePush(), member.isCommentPush());
    }
}
