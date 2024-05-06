package com.moing.backend.domain.missionComment.application.service;

import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.missionComment.domain.service.MissionCommentGetService;
import com.moing.backend.global.response.BaseMissionServiceResponse;
import com.moing.backend.global.utils.BaseMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetMissionCommentUseCase {

    private final MissionCommentGetService missionCommentGetService;
    private final BaseMissionService baseMissionService;

    /**
     * 게시글 댓글 전체 조회
     */
    public GetCommentResponse getBoardCommentAll(String socialId, Long teamId, Long missionArchiveId){
        BaseMissionServiceResponse data = baseMissionService.getCommonData(socialId, teamId, missionArchiveId);
        return missionCommentGetService.getCommentAll(missionArchiveId, data.getTeamMember());
    }
}
