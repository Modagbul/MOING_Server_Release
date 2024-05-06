package com.moing.backend.domain.missionComment.presentation;

import com.moing.backend.domain.boardComment.presentattion.constant.BoardCommentResponseMessage;
import com.moing.backend.domain.comment.application.dto.request.CreateCommentRequest;
import com.moing.backend.domain.comment.application.dto.response.CreateCommentResponse;
import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.missionComment.application.service.CreateMissionCommentUseCase;
import com.moing.backend.domain.missionComment.application.service.DeleteMissionCommentUseCase;
import com.moing.backend.domain.missionComment.application.service.GetMissionCommentUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.boardComment.presentattion.constant.BoardCommentResponseMessage.GET_BOARD_COMMENT_ALL_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/{missionArchiveId}/mcomment")
public class MissionCommentController {

    private final CreateMissionCommentUseCase createMissionCommentUseCase;
    private final DeleteMissionCommentUseCase deleteMissionCommentUseCase;
    private final GetMissionCommentUseCase getMissionCommentUseCase;

    /**
     * 댓글 생성
     * [POST] api/{teamId}/{missionArchiveId}/comment
     * 작성자 : 김민수
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateCommentResponse>> createMissionComment(@AuthenticationPrincipal User user,
                                                                                     @PathVariable Long teamId,
                                                                                     @PathVariable Long missionArchiveId,
                                                                                     @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return ResponseEntity.ok(SuccessResponse.create(BoardCommentResponseMessage.CREATE_BOARD_COMMENT_SUCCESS.getMessage(), this.createMissionCommentUseCase.createBoardComment(user.getSocialId(), teamId, missionArchiveId, createCommentRequest)));
    }

    /**
     * 댓글 삭제
     * [DELETE] api/{teamId}/{missionArchiveId}/comment/{commentId}
     * 작성자 : 김민수
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> deleteMissionComment(@AuthenticationPrincipal User user,
                                                              @PathVariable Long teamId,
                                                              @PathVariable Long missionArchiveId,
                                                              @PathVariable Long commentId) {
        this.deleteMissionCommentUseCase.deleteMissionComment(user.getSocialId(), teamId, missionArchiveId, commentId);
        return ResponseEntity.ok(SuccessResponse.create(BoardCommentResponseMessage.DELETE_BOARD_COMMENT_SUCCESS.getMessage()));
    }


    /**
     * 댓글 전체 조회
     * [GET] api/{teamId}/{missionArchiveId}/mcomment
     * 작성자 : 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<GetCommentResponse>> getMissionCommentAll(@AuthenticationPrincipal User user,
                                                                                  @PathVariable Long teamId,
                                                                                  @PathVariable Long missionArchiveId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_BOARD_COMMENT_ALL_SUCCESS.getMessage(), this.getMissionCommentUseCase.getBoardCommentAll(user.getSocialId(), teamId, missionArchiveId)));
    }
}
