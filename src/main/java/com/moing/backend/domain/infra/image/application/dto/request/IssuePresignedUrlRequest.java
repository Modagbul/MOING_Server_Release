package com.moing.backend.domain.infra.image.application.dto.request;


import com.moing.backend.domain.infra.image.application.dto.ImageFileExtension;
import com.moing.backend.global.annotation.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IssuePresignedUrlRequest {


    @NotBlank(message = "파일 확장자를 입력해주세요.")
    @ValidEnum(
            enumClass = ImageFileExtension.class,
            message = "유효하지 않은 ImageFileExtension 파라미터입니다.")
    private ImageFileExtension imageFileExtension;

}
