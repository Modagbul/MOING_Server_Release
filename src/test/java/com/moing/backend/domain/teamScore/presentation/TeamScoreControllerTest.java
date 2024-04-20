package com.moing.backend.domain.teamScore.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.application.service.TeamScoreGetUseCase;
import com.moing.backend.domain.teamScore.application.service.TeamScoreUpdateUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.moing.backend.domain.teamScore.presentation.constant.TeamScoreResponseMessage.GET_TEAMSCORE_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@WebMvcTest(TeamScoreController.class)
public class TeamScoreControllerTest extends CommonControllerTest {


    @MockBean
    private TeamScoreGetUseCase teamScoreGetUseCase;

    @Test
    public void 팀별_불_레벨_경험치_조회() throws Exception {
        //given

        TeamScoreRes output = TeamScoreRes.builder()
                .score(1L)
                .level(1L)
                .build();

        given(teamScoreGetUseCase.getTeamScoreInfo(any())).willReturn(output);

        Long teamId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}/my-fire",teamId)
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
                                        fieldWithPath("message").description(GET_TEAMSCORE_SUCCESS.getMessage()),
                                        fieldWithPath("data.score").description("팀 경험치"),
                                        fieldWithPath("data.level").description("팀 불 레벨")

                                )
                        )
                )
                .andReturn();

    }


}