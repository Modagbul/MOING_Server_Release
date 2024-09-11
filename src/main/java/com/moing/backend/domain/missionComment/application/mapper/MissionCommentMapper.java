package com.moing.backend.domain.missionComment.application.mapper;

import com.moing.backend.domain.comment.application.dto.request.CreateCommentRequest;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MissionCommentMapper {
    public static MissionComment toMissionComment(TeamMember teamMember, MissionArchive missionArchive, CreateCommentRequest createCommentRequest, boolean isLeader) {
        MissionComment missionComment=new MissionComment();
        missionComment.init(createCommentRequest.getContent(),isLeader);
        missionComment.updateMissionArchive(missionArchive);
        missionComment.updateTeamMember(teamMember);
        return missionComment;
    }
}