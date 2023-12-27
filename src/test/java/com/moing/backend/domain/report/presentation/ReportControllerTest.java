package com.moing.backend.domain.report.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.report.application.service.ReportCreateUseCase;
import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.presentation.TeamScoreController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.CREATE_REPORT_SUCCESS;
import static com.moing.backend.domain.teamScore.presentation.constant.TeamScoreResponseMessage.GET_TEAMSCORE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@WebMvcTest(ReportController.class)
public class ReportControllerTest extends CommonControllerTest {

    @MockBean
    private ReportCreateUseCase reportCreateUseCase;

    @Test
    public void 신고하기() throws Exception {
        //given

        Long targetId = 1L;

        given(reportCreateUseCase.createReport(any(),any(),any())).willReturn(targetId);


        String reportType = "MISSION";
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/report/{reportType}/{targetId}", reportType, targetId)
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
                                        parameterWithName("reportType").description("MISSION/BOARD/COMMENT"),
                                        parameterWithName("targetId").description("신고할 board,missionArchive,comment 아이디")
                                ),

                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(CREATE_REPORT_SUCCESS.getMessage()),
                                        fieldWithPath("data").description("신고한 게시글 번호")

                                )
                        )
                )
                .andReturn();

    }

}