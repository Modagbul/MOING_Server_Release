package com.moing.backend.domain.board.presentation;

import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.CreateBoardResponse;
import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.dto.response.GetBoardDetailResponse;
import com.moing.backend.domain.board.application.dto.response.UpdateBoardResponse;
import com.moing.backend.domain.board.application.service.CreateBoardUserCase;
import com.moing.backend.domain.board.application.service.GetBoardUserCase;
import com.moing.backend.domain.board.application.service.UpdateBoardUserCase;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.board.presentation.constant.BoardResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/board")
public class BoardController {

    private final CreateBoardUserCase createBoardUserCase;
    private final UpdateBoardUserCase updateBoardUserCase;
    private final GetBoardUserCase getBoardUserCase;

    /**
     * 게시글 생성
     * [POST] api/{teamId}/board
     * 작성자 : 김민수
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateBoardResponse>> createBoard(@AuthenticationPrincipal User user,
                                                                            @PathVariable Long teamId,
                                                                            @Valid @RequestBody CreateBoardRequest createBoardRequest) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_BOARD_SUCCESS.getMessage(), this.createBoardUserCase.createBoard(user.getSocialId(), teamId, createBoardRequest)));
    }

    /**
     * 게시글 수정
     * [PUT] api/{teamId}/board/{boardId}
     * 작성자 : 김민수
     */
    @PutMapping("/{boardId}")
    public ResponseEntity<SuccessResponse<UpdateBoardResponse>> updateBoard(@AuthenticationPrincipal User user,
                                                                            @PathVariable Long teamId,
                                                                            @PathVariable Long boardId,
                                                                            @Valid @RequestBody UpdateBoardRequest updateBoardRequest) {
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_BOARD_SUCCESS.getMessage(), this.updateBoardUserCase.updateBoard(user.getSocialId(), teamId, boardId, updateBoardRequest)));
    }

    /**
     * 게시글 상세 조회
     * [GET] api/{teamId}/board/{boardId}
     * 작성자 : 김민수
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<SuccessResponse<GetBoardDetailResponse>> getBoardDetail(@AuthenticationPrincipal User user,
                                                                                  @PathVariable Long teamId,
                                                                                  @PathVariable Long boardId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_BOARD_DETAIL_SUCCESS.getMessage(), this.getBoardUserCase.getBoardDetail(user.getSocialId(), teamId, boardId)));
    }

    /**
     * 게시글 전체 조회
     * [GET] api/{teamId}/board
     * 작성자 : 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<GetAllBoardResponse>> getBoardAll(@AuthenticationPrincipal User user,
                                                                               @PathVariable Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_BOARD_ALL_SUCCESS.getMessage(), this.getBoardUserCase.getAllBoard(user.getSocialId(), teamId)));
    }
}
