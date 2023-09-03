package com.moing.backend.domain.infra.image.presentation;

import com.moing.backend.domain.infra.image.application.dto.request.IssuePresignedUrlRequest;
import com.moing.backend.domain.infra.image.application.dto.response.IssuePresignedUrlResponse;
import com.moing.backend.domain.infra.image.application.service.IssuePresignedUrlUseCase;
import com.moing.backend.domain.infra.image.presentation.constant.EImageResponseMessage;
import com.moing.backend.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final IssuePresignedUrlUseCase getPresignedUrlUseCase;


    @PostMapping("/presigned")
    public ResponseEntity<SuccessResponse<IssuePresignedUrlResponse>> createPresigned(@RequestBody IssuePresignedUrlRequest issuePresignedUrlRequest) {

        return ResponseEntity.ok(SuccessResponse.create(EImageResponseMessage.ISSUE_PRESIGNED_URL_SUCCESS.getMessage(), getPresignedUrlUseCase.execute(issuePresignedUrlRequest)));
    }
}
