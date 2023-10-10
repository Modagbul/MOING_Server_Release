package com.moing.backend.domain.fire.application.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FireReceiveRes {

    private Long receiveMemberId;
    private String nickName;
    private String fireStatus;

    public void updateFireStatus(boolean status) {
        if (status)
            this.fireStatus = "True";
        else this.fireStatus = "False";
    }
}
