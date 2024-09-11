package com.moing.backend.domain.boardComment.presentattion;

import com.moing.backend.domain.boardComment.application.service.CreateBoardCommentUseCase;
import com.moing.backend.domain.boardComment.application.service.DeleteBoardCommentUseCase;
import com.moing.backend.domain.boardComment.application.service.GetBoardCommentUseCase;
import com.moing.backend.domain.boardComment.presentattion.constant.BoardCommentResponseMessage;
import com.moing.backend.domain.comment.application.dto.request.CreateCommentRequest;
import com.moing.backend.domain.comment.application.dto.response.CreateCommentResponse;
import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
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
@RequestMapping("/api/{teamId}/{boardId}/comment")
public class BoardCommentController {

    private final CreateBoardCommentUseCase createBoardCommentUseCase;
    private final DeleteBoardCommentUseCase deleteBoardCommentUseCase;
    private final GetBoardCommentUseCase getBoardCommentUseCase;

    /**
     * 댓글 생성
     * [POST] api/{teamId}/{boardId}/comment
     * 작성자 : 김민수
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateCommentResponse>> createBoardComment(@AuthenticationPrincipal User user,
                                                                                     @PathVariable Long teamId,
                                                                                     @PathVariable Long boardId,
                                                                                     @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return ResponseEntity.ok(SuccessResponse.create(BoardCommentResponseMessage.CREATE_BOARD_COMMENT_SUCCESS.getMessage(), this.createBoardCommentUseCase.createBoardComment(user.getSocialId(), teamId, boardId, createCommentRequest)));
    }

    /**
     * 댓글 삭제
     * [DELETE] api/{teamId}/{boardId}/comment/{commentId}
     * 작성자 : 김민수
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> deleteBoardComment(@AuthenticationPrincipal User user,
                                                              @PathVariable Long teamId,
                                                              @PathVariable Long boardId,
                                                              @PathVariable Long commentId) {
        this.deleteBoardCommentUseCase.deleteBoardComment(user.getSocialId(), teamId, boardId, commentId);
        return ResponseEntity.ok(SuccessResponse.create(BoardCommentResponseMessage.DELETE_BOARD_COMMENT_SUCCESS.getMessage()));
    }


    /**
     * 댓글 전체 조회
     * [GET] api/{teamId}/{boardId}/comment
     * 작성자 : 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<GetCommentResponse>> getBoardCommentAll(@AuthenticationPrincipal User user,
                                                                                  @PathVariable Long teamId,
                                                                                  @PathVariable Long boardId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_BOARD_COMMENT_ALL_SUCCESS.getMessage(), this.getBoardCommentUseCase.getBoardCommentAll(user.getSocialId(), teamId, boardId)));
    }
}
