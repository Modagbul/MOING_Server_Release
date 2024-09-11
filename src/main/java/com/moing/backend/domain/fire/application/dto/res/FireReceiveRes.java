package com.moing.backend.domain.fire.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FireReceiveRes {

    private Long receiveMemberId;
    private String nickname;
    private String fireStatus;
    private String profileImg;

    public FireReceiveRes(Long receiveMemberId, String nickname,String profileImg) {
        this.receiveMemberId = receiveMemberId;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public void updateFireStatus(boolean status) {
        if (status)
            this.fireStatus = "True";
        else this.fireStatus = "False";
    }
}
