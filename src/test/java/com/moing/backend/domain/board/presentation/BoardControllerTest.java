package com.moing.backend.domain.board.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.*;
import com.moing.backend.domain.board.application.service.CreateBoardUserCase;
import com.moing.backend.domain.board.application.service.GetBoardUserCase;
import com.moing.backend.domain.board.application.service.UpdateBoardUserCase;
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

@WebMvcTest(BoardController.class)
public class BoardControllerTest extends CommonControllerTest {
    @MockBean
    private CreateBoardUserCase createBoardUserCase;
    @MockBean
    private UpdateBoardUserCase updateBoardUserCase;

    @MockBean
    private GetBoardUserCase getBoardUserCase;

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
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디")
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
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("boardId").description("게시글 아이디")
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

    @Test
    public void get_board_all() throws Exception {
        //given
        List<BoardBlocks> noticeBlocks = new ArrayList<>();
        List<BoardBlocks> notNoticeBlocks = new ArrayList<>();
        Long teamId = 1L;

        BoardBlocks noticeBlock = BoardBlocks.builder()
                .boardId(1L)
                .writerIsLeader(true)
                .writerNickName("작성자 닉네임")
                .writerProfileImage("작성자 프로필 이미지")
                .title("공지 제목")
                .content("공지 내용")
                .commentNum(2)
                .isRead(false)
                .build();

        BoardBlocks notNoticeBlock = BoardBlocks.builder()
                .boardId(1L)
                .writerIsLeader(true)
                .writerNickName("작성자 닉네임")
                .writerProfileImage("작성자 프로필 이미지")
                .title("게시글 제목")
                .content("게시글 내용")
                .commentNum(2)
                .isRead(false)
                .build();

        noticeBlocks.add(noticeBlock);
        notNoticeBlocks.add(notNoticeBlock);

        GetAllBoardResponse output = new GetAllBoardResponse(noticeBlocks.size(), noticeBlocks, notNoticeBlocks.size(), notNoticeBlocks);

        given(getBoardUserCase.getAllBoard(any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/{teamId}/board", teamId)
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
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("게시글 목록을 모두 조회했습니다."),
                                        fieldWithPath("data.noticeNum").description("공지 개수"),
                                        fieldWithPath("data.noticeBlocks[0].boardId").description("공지 아이디"),
                                        fieldWithPath("data.noticeBlocks[0].writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.noticeBlocks[0].writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.noticeBlocks[0].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.noticeBlocks[0].title").description("공지 제목"),
                                        fieldWithPath("data.noticeBlocks[0].content").description("공지 내용"),
                                        fieldWithPath("data.noticeBlocks[0].commentNum").description("공지 댓글 개수"),
                                        fieldWithPath("data.noticeBlocks[0].isRead").description("공지 읽음 처리 여부"),
                                        fieldWithPath("data.notNoticeNum").description("일반 게시글 개수"),
                                        fieldWithPath("data.notNoticeBlocks[0].boardId").description("일반 게시글 아이디"),
                                        fieldWithPath("data.notNoticeBlocks[0].writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.notNoticeBlocks[0].writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.notNoticeBlocks[0].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.notNoticeBlocks[0].title").description("일반 게시글 제목"),
                                        fieldWithPath("data.notNoticeBlocks[0].content").description("일반 게시글 내용"),
                                        fieldWithPath("data.notNoticeBlocks[0].commentNum").description("일반 게시글 댓글 개수"),
                                        fieldWithPath("data.notNoticeBlocks[0].isRead").description("일반 게시글 읽음 처리 여부")
                                )

                        )
                );
    }

    @Test
    public void get_board_detail() throws Exception {
        //given
        Long teamId = 1L;
        Long boardId=1L;

        GetBoardDetailResponse output = GetBoardDetailResponse.builder()
                .boardId(1L)
                .writerIsLeader(true)
                .writerNickName("작성자 닉네임")
                .writerProfileImage("작성자 프로필 이미지")
                .title("게시글 제목")
                .content("게시글 내용")
                .createdDate("2023/09/29 23:42")
                .isWriter(false)
                .isNotice(false)
                .build();

        given(getBoardUserCase.getBoardDetail(any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/{teamId}/board/{boardId}", teamId, boardId)
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
                                        fieldWithPath("message").description("게시글 목록을 모두 조회했습니다."),
                                        fieldWithPath("data.boardId").description("게시글 아이디"),
                                        fieldWithPath("data.writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.title").description("게시글 제목"),
                                        fieldWithPath("data.content").description("게시글 내용"),
                                        fieldWithPath("data.createdDate").description("게시글 생성 날짜, 시간"),
                                        fieldWithPath("data.isWriter").description("게시글 작성자 여부"),
                                        fieldWithPath("data.isNotice").description("게시글 공지 여부")
                                )

                        )
                );
    }

}
