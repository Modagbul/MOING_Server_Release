package com.moing.backend.domain.history.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PagePath {

    NOTICE_PATH("/post/detail"),

    MISSION_PATH("/missions/prove"),

    MISSION_ALL_PTAH("/missions"),

    HOME_PATH("/home");


    private final String value;

}
