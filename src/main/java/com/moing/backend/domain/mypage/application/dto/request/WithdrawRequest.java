package com.moing.backend.domain.mypage.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WithdrawRequest {
    @NotBlank(message = "reason 을 입력해주세요.")
    @Size(min = 1, max = 500, message="reason 은 최소 1개, 최대 500개의 문자만 입력 가능합니다.")
    private String reason;
}
