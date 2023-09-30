package com.moing.backend.domain.boardComment.presentattion;

import com.moing.backend.domain.boardComment.application.dto.request.CreateBoardCommentRequest;
import com.moing.backend.domain.boardComment.application.dto.response.CreateBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.service.CreateBoardCommentUserCase;
import com.moing.backend.domain.boardComment.application.service.DeleteBoardCommentUserCase;
import com.moing.backend.domain.boardComment.application.service.GetBoardCommentUserCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.board.presentation.constant.BoardResponseMessage.GET_BOARD_ALL_SUCCESS;
import static com.moing.backend.domain.boardComment.presentattion.constant.BoardCommentResponseMessage.CREATE_BOARD_COMMENT_SUCCESS;
import static com.moing.backend.domain.boardComment.presentattion.constant.BoardCommentResponseMessage.DELETE_BOARD_COMMENT_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/{boardId}/comment")
public class BoardCommentController {

    private final CreateBoardCommentUserCase createBoardCommentUserCase;
    private final DeleteBoardCommentUserCase deleteBoardCommentUserCase;
    private final GetBoardCommentUserCase getBoardCommentUserCase;

    /**
     * 댓글 생성
     * [POST] api/{teamId}/{boardId}/comment
     * 작성자 : 김민수
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateBoardCommentResponse>> createBoardComment(@AuthenticationPrincipal User user,
                                                                                          @PathVariable Long teamId,
                                                                                          @PathVariable Long boardId,
                                                                                          @Valid @RequestBody CreateBoardCommentRequest createBoardCommentRequest) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_BOARD_COMMENT_SUCCESS.getMessage(), this.createBoardCommentUserCase.createBoardComment(user.getSocialId(), teamId, boardId, createBoardCommentRequest)));
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
        this.deleteBoardCommentUserCase.deleteBoardComment(user.getSocialId(), teamId, boardId, commentId);
        return ResponseEntity.ok(SuccessResponse.create(DELETE_BOARD_COMMENT_SUCCESS.getMessage()));
    }


    /**
     * 댓글 전체 조회
     * [GET] api/{teamId}/{boardId}/comment
     * 작성자 : 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<GetBoardCommentResponse>> getBoardCommentAll(@AuthenticationPrincipal User user,
                                                                                       @PathVariable Long teamId,
                                                                                       @PathVariable Long boardId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_BOARD_ALL_SUCCESS.getMessage(), this.getBoardCommentUserCase.getBoardCommentAll(user.getSocialId(), teamId, boardId)));
    }
}
