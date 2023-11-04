package com.moing.backend.domain.auth.application.dto.request;

import com.moing.backend.domain.member.domain.constant.Gender;
import com.moing.backend.domain.team.domain.constant.Category;
import com.moing.backend.global.annotation.Enum;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpRequest {

    @NotBlank(message = "nickName 을 입력해주세요.")
    @Size(min = 1, max = 10, message="nickName 은 최소 1개, 최대 10개의 문자만 입력 가능합니다.")
    private String nickName;

    @NotNull(message = "유효하지 않은 gender가 입력되었습니다.")
    private Gender gender;

    @NotNull(message="birthDate 을 입력해주세요.")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "날짜 형식이 잘못되었습니다. YYYY-MM-DD 형식으로 입력해주세요.")
    private String birthDate;
}
