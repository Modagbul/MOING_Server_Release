package com.moing.backend.domain.board.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.CreateBoardResponse;
import com.moing.backend.domain.board.application.dto.response.UpdateBoardResponse;
import com.moing.backend.domain.board.application.service.CreateBoardUserCase;
import com.moing.backend.domain.board.application.service.UpdateBoardUserCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
public class BoardControllerTest extends CommonControllerTest {
    @MockBean
    private CreateBoardUserCase createBoardUserCase;
    @MockBean
    private UpdateBoardUserCase updateBoardUserCase;

    @Test
    public void create_board() throws Exception {

        //given
        Long teamId = 1L;
        CreateBoardRequest input = CreateBoardRequest.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .isNotice(false)
                .build();

        String body = objectMapper.writeValueAsString(input);

        CreateBoardResponse output = CreateBoardResponse.builder()
                .boardId(1L)
                .build();

        given(createBoardUserCase.createBoard(any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/{teamId}/board", teamId)
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
                                requestFields(
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용"),
                                        fieldWithPath("isNotice").description("공지 여부")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("게시글을 생성했습니다"),
                                        fieldWithPath("data.boardId").description("생성한 boardId")
                                )
                        )
                );
    }

    @Test
    public void update_board() throws Exception {

        //given
        Long teamId = 1L;
        Long boardId = 1L;
        UpdateBoardRequest input = UpdateBoardRequest.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .isNotice(false)
                .build();

        String body = objectMapper.writeValueAsString(input);

        UpdateBoardResponse output = UpdateBoardResponse.builder()
                .boardId(1L)
                .build();

        given(updateBoardUserCase.updateBoard(any(), any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                put("/api/{teamId}/board/{boardId}", teamId, boardId)
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
                                requestFields(
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용"),
                                        fieldWithPath("isNotice").description("공지 여부")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("게시글을 수정했습니다"),
                                        fieldWithPath("data.boardId").description("수정한 boardId")
                                )
                        )
                );
    }
}
