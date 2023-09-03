package com.moing.backend.domain.infra.image.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.infra.image.application.dto.ImageFileExtension;
import com.moing.backend.domain.infra.image.application.dto.request.IssuePresignedUrlRequest;
import com.moing.backend.domain.infra.image.application.dto.response.IssuePresignedUrlResponse;
import com.moing.backend.domain.infra.image.application.service.IssuePresignedUrlUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
class ImageControllerTest extends CommonControllerTest {

    @MockBean
    private IssuePresignedUrlUseCase getPresignedUrlUseCase;

    @Test
    public void create_presigned_url() throws Exception {
        IssuePresignedUrlRequest input = IssuePresignedUrlRequest.builder()
                .imageFileExtension(ImageFileExtension.JPG)
                .build();

        String body = objectMapper.writeValueAsString(input);

        IssuePresignedUrlResponse output = IssuePresignedUrlResponse.builder()
                .presignedUrl("PRESIGNED_URL")
                .imgUrl("IMAGE_URL")
                .build();


        given(getPresignedUrlUseCase.execute(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/image/presigned")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );


        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("imageFileExtension").description("이미지 확장자")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("presignedUrl을 발급하였습니다"),
                                        fieldWithPath("data.presignedUrl").description("이미지 업로드용 PresignedUrl"),
                                                fieldWithPath("data.imgUrl").description("업로드 후 이미지 URL")
                                        )
                                )
                        );
    }
}