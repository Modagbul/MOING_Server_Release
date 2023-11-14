package com.moing.backend.domain.fire.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.global.config.fcm.dto.request.SingleRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.moing.backend.global.config.fcm.constant.FireThrowMessage.*;
import static java.lang.Math.random;

@Service
@Transactional
@RequiredArgsConstructor
public class FireThrowAlarmUseCase {

    private final FcmService fcmService;

    public void sendFireThrowAlarm(Member throwMember, Member receiveMember) {
        String title = getTitle(throwMember.getNickName(), receiveMember.getNickName(), (int) random() * 2);

        String message = getMessage(throwMember.getNickName(), receiveMember.getNickName(), (int) random() * 2);
        SingleRequest singleRequest = new SingleRequest(receiveMember.getFcmToken(), title, message);

        fcmService.sendSingleDevice(singleRequest);
    }

    public String getMessage(String pusher, String receiver, int num) {

        switch (num) {
            case 0: return pusher + "님이" + receiver + NEW_FIRE_THROW_MESSAGE1.getMessage();
            case 1: return receiver + "님! " + pusher + NEW_FIRE_THROW_MESSAGE2.getMessage();
        }
        return pusher + "님이" + receiver + NEW_FIRE_THROW_MESSAGE1.getMessage();
    }

    public String getTitle(String pusher, String receiver, int num) {

        switch (num) {
            case 0:
                return NEW_FIRE_THROW_TITLE1.getMessage();
            case 1:
                return NEW_FIRE_THROW_TITLE2.getMessage();
        }
        return NEW_FIRE_THROW_TITLE1.getMessage();
    }


}
