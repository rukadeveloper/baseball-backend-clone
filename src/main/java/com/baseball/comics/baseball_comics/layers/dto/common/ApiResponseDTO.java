package com.baseball.comics.baseball_comics.layers.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class ApiResponseDTO<T> {

    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponseDTO<T> success(MessageType messageType, T data) {
        return ApiResponseDTO.<T>builder().status("success").message(messageType.getMessage())
                .data(data).timestamp(LocalDateTime.now()).build();
    }

    public static <T> ApiResponseDTO<T> success(MessageType messageType) {
        return ApiResponseDTO.<T>builder().status("success").message(messageType.getMessage())
                .timestamp(LocalDateTime.now()).build();
    }
}
