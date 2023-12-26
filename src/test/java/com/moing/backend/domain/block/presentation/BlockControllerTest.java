package com.moing.backend.domain.block.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.block.application.service.BlockCreateUseCase;
import com.moing.backend.domain.block.application.service.BlockDeleteUseCase;
import com.moing.backend.domain.block.application.service.BlockReadUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.moing.backend.domain.block.presentation.constant.BlockResponseMessage.*;
import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.CREATE_REPORT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@WebMvcTest(BlockController.class)
class BlockControllerTest extends CommonControllerTest {

    @MockBean
    private BlockReadUseCase blockReadUseCase;
    @MockBean
    private BlockCreateUseCase blockCreateUseCase;
    @MockBean
    private BlockDeleteUseCase blockDeleteUseCase;

    @Test
    public void 차단_하기() throws Exception{

        Long targetId = 1L;

        given(blockCreateUseCase.createBlock(any(),any())).willReturn(targetId);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/block/{targetId}", targetId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)

        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),

                                pathParameters(
                                        parameterWithName("targetId").description("신고할 사용자 아이디")
                                ),

                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(CREATE_BLOCK_SUCCESS.getMessage()),
                                        fieldWithPath("data").description("신고한 유저 번호")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 차단_해제_하기() throws Exception{

        Long targetId = 1L;

        given(blockDeleteUseCase.deleteBlock(any(),any())).willReturn(targetId);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/block/{targetId}", targetId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)

        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),

                                pathParameters(
                                        parameterWithName("targetId").description("신고할 사용자 아이디")
                                ),

                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(DELETE_BLOCK_SUCCESS.getMessage()),
                                        fieldWithPath("data").description("신고한 유저 번호")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 차단한_유저_목록() throws Exception{


        List<Long> response = new ArrayList<>();

        given(blockReadUseCase.getMyBlockList(any())).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/block/")
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)

        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),

                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(GET_BLOCK_SUCCESS.getMessage()),
                                        fieldWithPath("data").description("차단한 유저 목록")

                                )
                        )
                )
                .andReturn();

    }



}