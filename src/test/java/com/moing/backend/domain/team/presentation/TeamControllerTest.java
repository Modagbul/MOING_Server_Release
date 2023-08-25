package com.moing.backend.domain.team.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.CreateTeamResponse;
import com.moing.backend.domain.team.application.service.CreateTeamUserCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
public class TeamControllerTest extends CommonControllerTest {
    @MockBean
    private CreateTeamUserCase createTeamService;

    @Test
    public void create_team() throws Exception {

        //given
        CreateTeamRequest input = CreateTeamRequest.builder()
                .category("ETC")
                .name("소모임 이름")
                .introduction("소모임 소개글")
                .profileImgUrl("소모임 대표 사진 URL")
                .promise("소모임 각오")
                .build();

        String body = objectMapper.writeValueAsString(input);

        CreateTeamResponse output = CreateTeamResponse.builder()
                .teamId(1L)
                .build();

        given(createTeamService.createTeam(any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(
                post("/api/team")
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
                                        fieldWithPath("category").description("카테고리"),
                                        fieldWithPath("name").description("소모임 이름"),
                                        fieldWithPath("introduction").description("소모임 소개글"),
                                        fieldWithPath("profileImgUrl").description("소모임 대표 사진"),
                                        fieldWithPath("promise").description("소모임 각오")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("소모임을 생성하였습니다"),
                                        fieldWithPath("data.teamId").description("생성한 teamId")
                                )
                        )
                );
    }
}
