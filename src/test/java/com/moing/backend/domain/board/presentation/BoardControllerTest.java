package com.moing.backend.domain.board.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.*;
import com.moing.backend.domain.board.application.service.CreateBoardUseCase;
import com.moing.backend.domain.board.application.service.DeleteBoardUseCase;
import com.moing.backend.domain.board.application.service.GetBoardUseCase;
import com.moing.backend.domain.board.application.service.UpdateBoardUseCase;
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
    private CreateBoardUseCase createBoardUseCase;
    @MockBean
    private UpdateBoardUseCase updateBoardUseCase;
    @MockBean
    private GetBoardUseCase getBoardUseCase;
    @MockBean
    private DeleteBoardUseCase deleteBoardUseCase;

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

        given(createBoardUseCase.createBoard(any(), any(), any())).willReturn(output);


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

        given(updateBoardUseCase.updateBoard(any(), any(), any(), any())).willReturn(output);


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
    public void delete_board() throws Exception {

        //given
        Long teamId = 1L;
        Long boardId = 1L;


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/{teamId}/board/{boardId}", teamId, boardId)
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
                                        fieldWithPath("message").description("게시글을 삭제했습니다")
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
                .writerIsDeleted(false)
                .writerProfileImage("작성자 프로필 이미지")
                .title("공지 제목")
                .content("공지 내용")
                .commentNum(2)
                .isRead(false)
                .isNotice(true)
                .makerId(1L)
                .build();

        BoardBlocks notNoticeBlock = BoardBlocks.builder()
                .boardId(1L)
                .writerIsLeader(true)
                .writerNickName("작성자 닉네임")
                .writerIsDeleted(false)
                .writerProfileImage("작성자 프로필 이미지")
                .title("게시글 제목")
                .content("게시글 내용")
                .commentNum(2)
                .isRead(false)
                .isNotice(false)
                .makerId(1L)
                .build();

        noticeBlocks.add(noticeBlock);
        notNoticeBlocks.add(notNoticeBlock);

        GetAllBoardResponse output = new GetAllBoardResponse(noticeBlocks.size(), noticeBlocks, notNoticeBlocks.size(), notNoticeBlocks);

        given(getBoardUseCase.getAllBoard(any(), any())).willReturn(output);


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
                                        fieldWithPath("data.noticeBlocks[].boardId").description("공지 아이디"),
                                        fieldWithPath("data.noticeBlocks[].writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.noticeBlocks[].writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.noticeBlocks[].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.noticeBlocks[].writerIsDeleted").description("작성자 삭제 여부"),
                                        fieldWithPath("data.noticeBlocks[].title").description("공지 제목"),
                                        fieldWithPath("data.noticeBlocks[].content").description("공지 내용"),
                                        fieldWithPath("data.noticeBlocks[].commentNum").description("공지 댓글 개수"),
                                        fieldWithPath("data.noticeBlocks[].isRead").description("공지 읽음 처리 여부"),
                                        fieldWithPath("data.noticeBlocks[].notice").description("true"),
                                        fieldWithPath("data.noticeBlocks[].makerId").description("작성자 Id"),
                                        fieldWithPath("data.notNoticeNum").description("일반 게시글 개수"),
                                        fieldWithPath("data.notNoticeBlocks[].boardId").description("일반 게시글 아이디"),
                                        fieldWithPath("data.notNoticeBlocks[].writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.notNoticeBlocks[].writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.notNoticeBlocks[].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.notNoticeBlocks[].writerIsDeleted").description("작성자 삭제 여부"),
                                        fieldWithPath("data.notNoticeBlocks[].title").description("일반 게시글 제목"),
                                        fieldWithPath("data.notNoticeBlocks[].content").description("일반 게시글 내용"),
                                        fieldWithPath("data.notNoticeBlocks[].commentNum").description("일반 게시글 댓글 개수"),
                                        fieldWithPath("data.notNoticeBlocks[].isRead").description("일반 게시글 읽음 처리 여부"),
                                        fieldWithPath("data.notNoticeBlocks[].notice").description("false"),
                                        fieldWithPath("data.notNoticeBlocks[].makerId").description("작성자 Id")
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
                .makerId(1L)
                .build();

        given(getBoardUseCase.getBoardDetail(any(), any(), any())).willReturn(output);


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
                                        fieldWithPath("message").description("게시글 상세 조회했습니다."),
                                        fieldWithPath("data.boardId").description("게시글 아이디"),
                                        fieldWithPath("data.writerNickName").description("작성자 닉네임"),
                                        fieldWithPath("data.writerIsLeader").description("작성자 소모임장 여부"),
                                        fieldWithPath("data.writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("data.title").description("게시글 제목"),
                                        fieldWithPath("data.content").description("게시글 내용"),
                                        fieldWithPath("data.createdDate").description("게시글 생성 날짜, 시간"),
                                        fieldWithPath("data.isWriter").description("게시글 작성자 여부"),
                                        fieldWithPath("data.isNotice").description("게시글 공지 여부"),
                                        fieldWithPath("data.makerId").description("작성자 아이디")
                                        
                                )

                        )
                );
    }

}
