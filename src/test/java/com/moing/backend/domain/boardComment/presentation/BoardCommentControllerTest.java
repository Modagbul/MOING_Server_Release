package com.moing.backend.domain.boardComment.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.boardComment.application.dto.request.CreateBoardCommentRequest;
import com.moing.backend.domain.boardComment.application.dto.response.CommentBlocks;
import com.moing.backend.domain.boardComment.application.dto.response.CreateBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.dto.response.GetBoardCommentResponse;
import com.moing.backend.domain.boardComment.application.service.CreateBoardCommentUserCase;
import com.moing.backend.domain.boardComment.application.service.DeleteBoardCommentUserCase;
import com.moing.backend.domain.boardComment.application.service.GetBoardCommentUserCase;
import com.moing.backend.domain.boardComment.presentattion.BoardCommentController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardCommentController.class)
public class BoardCommentControllerTest extends CommonControllerTest {
    @MockBean
    private CreateBoardCommentUserCase createBoardCommentUserCase;
    @MockBean
    private DeleteBoardCommentUserCase deleteBoardCommentUserCase;
    @MockBean
    private GetBoardCommentUserCase getBoardCommentUserCase;

    @Test
    public void create_board_comment() throws Exception {

        //given
        Long teamId = 1L;
        Long boardId = 1L;
        CreateBoardCommentRequest input = CreateBoardCommentRequest.builder()
                .content("게시글 내용")
                .build();

        String body = objectMapper.writeValueAsString(input);

        CreateBoardCommentResponse output = CreateBoardCommentResponse.builder()
                .boardCommentId(1L)
                .build();

        given(createBoardCommentUserCase.createBoardComment(any(), any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/{teamId}/{boardId}/comment", teamId, boardId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("boardId").description("게시글 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("content").description("댓글 내용")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("댓글을 생성했습니다"),
                                        fieldWithPath("data.boardCommentId").description("생성한 boardCommentId")
                                )
                        )
                );
    }

    @Test
    public void delete_board_comment() throws Exception {

        //given
        Long teamId = 1L;
        Long boardId = 1L;
        Long boardCommentId = 1L;


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/{teamId}/{boardId}/comment/{boardCommentId}", teamId, boardId, boardCommentId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("boardId").description("게시글 아이디"),
                                        parameterWithName("boardCommentId").description("댓글 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("댓글을 삭제했습니다")
                                )
                        )
                );
    }


    @Test
    public void get_board_comment_all() throws Exception {
        //given
        List<CommentBlocks> commentBlocks = new ArrayList<>();
        Long teamId = 1L;
        Long boardId = 1L;

        CommentBlocks commentBlock = CommentBlocks.builder()
                .boardCommentId(1L)
                .content("댓글 내용")
                .writerIsLeader(true)
                .writerNickName("작성자 닉네임")
                .writerProfileImage("작성자 프로필 이미지")
                .isWriter(true)
                .build();

        commentBlocks.add(commentBlock);

        GetBoardCommentResponse output = new GetBoardCommentResponse(commentBlocks);

        given(getBoardCommentUserCase.getBoardCommentAll(any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/{teamId}/{boardId}/comment", teamId, boardId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
        );


        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("boardId").description("게시글 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("댓글 목록을 모두 조회했습니다."),
                                        fieldWithPath("data.commentBlocks[0].boardCommentId").description("댓글 아이디"),
                                        fieldWithPath("data.commentBlocks[0].content").description("댓글 내용"),
                                        fieldWithPath("data.commentBlocks[0].writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.commentBlocks[0].writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.commentBlocks[0].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.commentBlocks[0].isWriter").description("댓글 작성자 여부")
                                )

                        )
                );
    }
}