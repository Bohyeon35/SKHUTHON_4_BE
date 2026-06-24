package com.skhuthon.team4.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseTemplate<T> {

    private final int status;
    private final String message;
    private final T data;

    public static <T> ApiResponseTemplate<T> success(T data) {
        return ApiResponseTemplate.<T>builder()
                .status(200)
                .message("success")
                .data(data)
                .build();
    }

    public static <T> ApiResponseTemplate<T> success() {
        return ApiResponseTemplate.<T>builder()
                .status(200)
                .message("success")
                .build();
    }

    public static <T> ApiResponseTemplate<T> error(int status, String message) {
        return ApiResponseTemplate.<T>builder()
                .status(status)
                .message(message)
                .build();
    }
}