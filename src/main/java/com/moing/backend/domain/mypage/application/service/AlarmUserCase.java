package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.response.GetAlarmResponse;
import com.moing.backend.domain.mypage.exception.AlarmInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmUserCase {

    private final MemberGetService memberGetService;

    public GetAlarmResponse getAlarm(String socialId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return new GetAlarmResponse(member.isNewUploadPush(),member.isRemindPush(), member.isFirePush());
    }

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
            default:
                throw new AlarmInvalidException();
        }
        return new GetAlarmResponse(member.isNewUploadPush(),member.isRemindPush(), member.isFirePush());
    }
}
