package com.moing.backend.domain.infra.image.application.service;


import com.moing.backend.domain.infra.image.application.dto.request.IssuePresignedUrlRequest;
import com.moing.backend.domain.infra.image.application.dto.response.IssuePresignedUrlResponse;
import com.moing.backend.global.config.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IssuePresignedUrlUseCase {

    private final S3Service s3Service;

    public IssuePresignedUrlResponse execute(IssuePresignedUrlRequest issuePresignedUrlRequest) {

        return IssuePresignedUrlResponse.from(
                s3Service.issuePreSignedUrl(
                        issuePresignedUrlRequest.getImageFileExtension()));
    }
}
