package com.moing.backend.domain.infra.image.application.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUrlDto {

    private final String presignedUrl;
    private final String key;

    public static ImageUrlDto of(String url, String key) {
        return ImageUrlDto.builder().presignedUrl(url).key(key).build();
    }
}
