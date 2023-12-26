package com.moing.backend.domain.report.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BlockMemberRes {
    private Long targetId;
    private String nickName;
    private String introduce;
    private String profileImg;
}
