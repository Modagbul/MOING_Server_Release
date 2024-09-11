package com.moing.backend.domain.infra.image.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFileExtension {
    JPEG("jpeg"),
    JPG("jpg"),
    PNG("png");

    private final String uploadExtension;
}
