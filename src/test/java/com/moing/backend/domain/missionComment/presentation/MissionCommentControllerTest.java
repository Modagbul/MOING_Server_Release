package com.moing.backend.domain.missionComment.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.comment.application.dto.request.CreateCommentRequest;
import com.moing.backend.domain.comment.application.dto.response.CommentBlocks;
import com.moing.backend.domain.comment.application.dto.response.CreateCommentResponse;
import com.moing.backend.domain.comment.application.dto.response.GetCommentResponse;
import com.moing.backend.domain.missionComment.application.service.CreateMissionCommentUseCase;
import com.moing.backend.domain.missionComment.application.service.DeleteMissionCommentUseCase;
import com.moing.backend.domain.missionComment.application.service.GetMissionCommentUseCase;
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

@WebMvcTest(MissionCommentController.class)
public class MissionCommentControllerTest extends CommonControllerTest {

    @MockBean
    private CreateMissionCommentUseCase createMissionCommentUseCase;
    @MockBean
    private DeleteMissionCommentUseCase deleteMissionCommentUseCase;
    @MockBean
    private GetMissionCommentUseCase getMissionCommentUseCase;

    @Test
    public void create_mission_comment() throws Exception {

        //given
        Long teamId = 1L;
        Long missionArchiveId = 1L;
        CreateCommentRequest input = CreateCommentRequest.builder()
                .content("게시글 내용")
                .build();

        String body = objectMapper.writeValueAsString(input);

        CreateCommentResponse output = CreateCommentResponse.builder()
                .commentId(1L)
                .build();

        given(createMissionCommentUseCase.createBoardComment(any(), any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/{teamId}/{missionArchiveId}/mcomment", teamId, missionArchiveId)
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
                                        parameterWithName("missionArchiveId").description("미션 게시물 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("content").description("댓글 내용")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("댓글을 생성했습니다"),
                                        fieldWithPath("data.commentId").description("생성한 boardCommentId")
                                )
                        )
                );
    }

    @Test
    public void delete_mission_comment() throws Exception {

        //given
        Long teamId = 1L;
        Long boardId = 1L;
        Long missionArchiveId = 1L;


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/{teamId}/{missionArchiveId}/mcomment/{boardCommentId}", teamId, boardId, missionArchiveId)
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
                                        parameterWithName("missionArchiveId").description("미션 게시글 아이디"),
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
                .commentId(1L)
                .content("댓글 내용")
                .writerIsLeader(true)
                .writerNickName("작성자 닉네임")
                .writerProfileImage("작성자 프로필 이미지")
                .writerIsDeleted(false)
                .isWriter(true)
                .createdDate("2023/12/05 23:29")
                .makerId(1L)
                .build();

        commentBlocks.add(commentBlock);

        GetCommentResponse output = new GetCommentResponse(commentBlocks);

        given(getMissionCommentUseCase.getBoardCommentAll(any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/{teamId}/{missionArchiveId}/mcomment", teamId, boardId)
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
                                        parameterWithName("missionArchiveId").description("게시글 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("댓글 목록을 모두 조회했습니다."),
                                        fieldWithPath("data.commentBlocks[].commentId").description("댓글 아이디"),
                                        fieldWithPath("data.commentBlocks[].content").description("댓글 내용"),
                                        fieldWithPath("data.commentBlocks[].writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.commentBlocks[].writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.commentBlocks[].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.commentBlocks[].writerIsDeleted").description("작성자 삭제 여부"),
                                        fieldWithPath("data.commentBlocks[].isWriter").description("댓글 작성자 여부"),
                                        fieldWithPath("data.commentBlocks[].createdDate").description("생성 시간"),
                                        fieldWithPath("data.commentBlocks[].makerId").description("작성자 Id")
                                )

                        )
                );
    }
}
