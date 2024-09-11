package com.moing.backend.domain.history.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.history.application.dto.response.GetAlarmCountResponse;
import com.moing.backend.domain.history.application.dto.response.GetAlarmHistoryResponse;
import com.moing.backend.domain.history.application.service.GetAlarmHistoryUseCase;
import com.moing.backend.domain.history.application.service.ReadAlarmHistoryUseCase;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.moing.backend.domain.history.presentation.constant.AlarmHistoryResponseMessage.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AlarmHistoryController.class)
class AlarmHistoryControllerTest extends CommonControllerTest {

    @MockBean
    private GetAlarmHistoryUseCase getAlarmHistoryUseCase;

    @MockBean
    private ReadAlarmHistoryUseCase readAlarmHistoryUseCase;

    @Test
    public void get_all_alarm_history() throws Exception {
        //given
        List<GetAlarmHistoryResponse> output = Lists.newArrayList(GetAlarmHistoryResponse.builder()
                .alarmHistoryId(1L)
                .type(AlarmType.NEW_UPLOAD)
                .path("/post/detail")
                .idInfo("{\"teamId\":74,\"boardId\":96}")
                        .title("갓생살자에 새로 올라온 공지를 확인하세요!")
                        .body("모임 장소 공지")
                        .name("모닥모닥불")
                        .isRead(false)
                        .createdDate("오후 06:39")
                .build());

        given(getAlarmHistoryUseCase.getAllAlarmHistories(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                get("/api/history/alarm")
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
                                        fieldWithPath("message").description(GET_ALL_ALARM_HISTORY.getMessage()),
                                        fieldWithPath("data[].alarmHistoryId").description("알림 히스토리 아이디"),
                                        fieldWithPath("data[].type").description("알림 아이콘 구분"),
                                        fieldWithPath("data[].path").description("알림 이동 페이지 path"),
                                        fieldWithPath("data[].idInfo").description("알림 페이지 이동할 때 필요한 data (JSON String)"),
                                        fieldWithPath("data[].title").description("알림의 제목 (가장 bold 처리 된거)"),
                                        fieldWithPath("data[].body").description("알림의 본문 (제목 밑)"),
                                        fieldWithPath("data[].name").description("미션 리마인드 제외하고는 팀 이름 (제목 위)"),
                                        fieldWithPath("data[].createdDate").description("작성 시간"),
                                        fieldWithPath("data[].isRead").description("읽음여부")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void read_alarm_history() throws Exception {

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/history/alarm/read?alarmHistoryId=1")
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
                                        fieldWithPath("message").description(READ_ALARM_HISTORY.getMessage())

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void get_alarm_count() throws Exception {
        //given
        GetAlarmCountResponse output = new GetAlarmCountResponse("99+");

        given(getAlarmHistoryUseCase.getUnreadAlarmCount(any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(
                get("/api/history/alarm/count")
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
                                        fieldWithPath("message").description(GET_UNREAD_ALARM_HISTORY.getMessage()),
                                        fieldWithPath("data.count").description("안 읽음 알림 개수 (99보다 크면 99+)")


                                )
                        )
                )
                .andReturn();

    }

}