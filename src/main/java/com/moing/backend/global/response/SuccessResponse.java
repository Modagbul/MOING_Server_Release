package com.moing.backend.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> {
    private Boolean isSuccess;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> SuccessResponse<T> create(String message) {
        return new SuccessResponse<>(true, message, null);
    }

    public static <T> SuccessResponse<T> create(String message, T data) {
        return new SuccessResponse<>(true, message, data);
    }
}