package com.moing.backend.domain.infra.image.application.dto.response;


import com.moing.backend.domain.infra.image.application.dto.ImageUrlDto;
import com.moing.backend.global.config.s3.ImageUrlUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssuePresignedUrlResponse {

    private final String presignedUrl;

    private final String imgUrl;

    public static IssuePresignedUrlResponse from(ImageUrlDto urlDto) {
        String imgUrl = ImageUrlUtil.prefix +"/"+ urlDto.getKey();

        return IssuePresignedUrlResponse.builder()
                .presignedUrl(urlDto.getPresignedUrl())
                .imgUrl(imgUrl)
                .build();
    }
}
