package com.moing.backend.domain.team.application.dto.response;

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
    private LocalDateTime deletionTime;
    private String profileImgUrl;

    @QueryProjection
    public TeamBlock(Long teamId, LocalDateTime approvalTime, Integer levelOfFire, String teamName, Integer numOfMember, String category, LocalDateTime deletionTime, String profileImgUrl) {
        this.teamId=teamId;
        this.duration=calculateDuration(approvalTime);
        this.levelOfFire=levelOfFire;
        this.teamName=teamName;
        this.numOfMember=numOfMember;
        this.category = category;
        this.startDate=approvalTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.deletionTime=deletionTime;
        this.profileImgUrl = profileImgUrl;
    }

    public Long calculateDuration(LocalDateTime approvalTime) {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime currentDateTime = LocalDateTime.now(seoulZoneId);

        long hoursBetween = ChronoUnit.HOURS.between(approvalTime, currentDateTime);
        long daysBetween = hoursBetween / 24;

        return daysBetween;
    }

}

