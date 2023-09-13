package com.moing.backend.domain.team.application.dto.response;

import com.moing.backend.domain.team.domain.constant.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TeamBlock {
    private Long teamId;
    private Long duration; //걸린시간(단위:날짜)
    private Integer levelOfFire; //불꽃 레벨
    private String teamName;
    private Integer numOfMember;
    private String category;
    private String startDate;

    @QueryProjection
    public TeamBlock(Long teamId, LocalDateTime approvalTime, Integer levelOfFire, String teamName, Integer numOfMember, Category category){
        this.teamId=teamId;
        this.duration=calculateDuration(approvalTime);
        this.levelOfFire=levelOfFire;
        this.teamName=teamName;
        this.numOfMember=numOfMember;
        this.category=category.getMessage();
        this.startDate=approvalTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    private Long calculateDuration(LocalDateTime approvalTime) {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime currentSeoulTime = LocalDateTime.now(seoulZoneId).withSecond(0).withNano(0);
        LocalDateTime adjustedApprovalTime = approvalTime.withSecond(0).withNano(0);
        return ChronoUnit.DAYS.between(adjustedApprovalTime, currentSeoulTime);
    }
}

