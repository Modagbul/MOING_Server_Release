package com.moing.backend.domain.fire.application.mapper;

import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.member.domain.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class FireMapper {

    public static FireThrowRes mapToFireThrowRes(Fire fire) {
        return FireThrowRes.builder()
                .receiveMemberId(fire.getReceiveMemberId())
                .build();
    }

    public static List<FireReceiveRes> mapToFireReceiversList(List<Member> members) {
        List<FireReceiveRes> fireReceiveResList = new ArrayList<>();
        members.forEach(
                member -> fireReceiveResList.add(FireMapper.mapToFireReceiveRes(member))
        );
        return fireReceiveResList;
    }

    public static FireReceiveRes mapToFireReceiveRes(Member member) {
        return FireReceiveRes.builder()
                .receiveMemberId(member.getMemberId())
                .nickname(member.getNickName())
                .fireStatus("TRUE")
                .build();
    }
}
