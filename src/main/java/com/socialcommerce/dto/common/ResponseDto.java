package com.socialcommerce.dto.common;

public class ResponseDto<T> {
    private int status;
    private String message;
    private T data;

    public ResponseDto(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
