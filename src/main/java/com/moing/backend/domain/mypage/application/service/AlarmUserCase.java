package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.application.mapper.MemberMapper;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.response.GetAlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmUserCase {

    private MemberGetService memberGetService;

    public GetAlarmResponse getAlarm(String socialId){
        Member member=memberGetService.getMemberBySocialId(socialId);
        return new GetAlarmResponse(member.isNewUploadPush(),member.isRemindPush(), member.isFirePush());
    }
}
