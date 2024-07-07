package com.moing.backend.domain.missionComment.domain.service;

import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.comment.domain.service.CommentGetService;
import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
import com.moing.backend.domain.missionComment.domain.entity.MissionComment;
import com.moing.backend.domain.missionComment.domain.repository.MissionCommentRepository;
import com.moing.backend.domain.missionComment.exception.NotFoundByMissionCommentIdException;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
@DomainService
@RequiredArgsConstructor
public class MissionCommentGetService implements CommentGetService<MissionComment> {
    private final MissionCommentRepository missionCommentRepository;

    @Override
    public MissionComment getComment(Long commentId) {
        return missionCommentRepository.findMissionCommentByMissionCommentId(commentId).orElseThrow(NotFoundByMissionCommentIdException::new);
    }

    @Override
    public GetCommentResponse getCommentAll(Long missionArchiveId, TeamMember teamMember) {
        return missionCommentRepository.findMissionCommentAll(missionArchiveId, teamMember);
    }

    @Override
    public Optional<List<NewUploadInfo>> getNewUploadInfo(Long memberId, Long missionArchiveId) {
        return missionCommentRepository.findNewUploadInfo(memberId, missionArchiveId);
    }
}
