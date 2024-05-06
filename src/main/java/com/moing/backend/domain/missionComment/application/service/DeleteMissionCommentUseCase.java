package com.moing.backend.domain.missionComment.application.service;

import com.moing.backend.domain.boardComment.exception.NotAuthByBoardCommentException;
import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.missionComment.domain.service.MissionCommentDeleteService;
import com.moing.backend.domain.missionComment.domain.service.MissionCommentGetService;
import com.moing.backend.global.response.BaseMissionServiceResponse;
import com.moing.backend.global.utils.BaseMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteMissionCommentUseCase {

    private final MissionCommentGetService missionCommentGetService;
    private final MissionCommentDeleteService missionCommentDeleteService;
    private final BaseMissionService baseMissionService;

    /**
     * 게시글 댓글 삭제
     */

    public void deleteMissionComment(String socialId, Long teamId, Long missionArchiveId, Long boardCommentId){
        // 1. 게시글 댓글 조회
        BaseMissionServiceResponse data = baseMissionService.getCommonData(socialId, teamId, missionArchiveId);
        MissionComment missionComment =missionCommentGetService.getComment(boardCommentId);
        // 2. 게시글 댓글 작성자만
        if (data.getTeamMember() == missionComment.getTeamMember()) {
            // 3. 삭제
            missionCommentDeleteService.deleteComment(missionComment);
            // 4. 댓글 개수 줄이기
            data.getMissionArchive().decrComNum();
        } else throw new NotAuthByBoardCommentException();
    }
}
