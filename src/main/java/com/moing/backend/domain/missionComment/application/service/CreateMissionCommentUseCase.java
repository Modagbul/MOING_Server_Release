package com.moing.backend.domain.missionComment.application.service;

import com.moing.backend.domain.comment.application.dto.request.CreateCommentRequest;
import com.moing.backend.domain.comment.application.dto.response.CreateCommentResponse;
import com.moing.backend.domain.missionComment.application.mapper.MissionCommentMapper;
import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.missionComment.domain.service.MissionCommentSaveService;
import com.moing.backend.domain.team.application.service.CheckLeaderUseCase;
import com.moing.backend.global.response.BaseMissionServiceResponse;
import com.moing.backend.global.utils.BaseMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateMissionCommentUseCase {

    private final MissionCommentSaveService missionCommentSaveService;
    private final BaseMissionService baseMissionService;
    private final CheckLeaderUseCase checkLeaderUseCase;
    private final SendMissionCommentAlarmUseCase sendCommentAlarm;
    /**
     * 게시글 댓글 생성
     */
    public CreateCommentResponse createBoardComment(String socialId, Long teamId, Long missionArchiveId, CreateCommentRequest createCommentRequest) {
        // 1. 미션 게시글 댓글 생성
        BaseMissionServiceResponse data = baseMissionService.getCommonData(socialId, teamId, missionArchiveId);
        boolean isLeader = checkLeaderUseCase.isTeamLeader(data.getMember(), data.getTeam());
        MissionComment missionComment = missionCommentSaveService.saveComment(MissionCommentMapper.toMissionComment(data.getTeamMember(), data.getMissionArchive(), createCommentRequest, isLeader));
        // 2. 미션 게시글 댓글 개수 증가
        data.getMissionArchive().incrComNum();
        // 3. 미션 게시글 댓글 알림
        sendCommentAlarm.sendCommentAlarm(data, missionComment);
        return new CreateCommentResponse(missionComment.getMissionCommentId());
    }
}

