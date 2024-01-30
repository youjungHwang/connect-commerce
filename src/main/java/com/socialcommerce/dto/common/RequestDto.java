package com.socialcommerce.dto.common;

public class RequestDto<T> {
    private T data;

    public RequestDto(T data) {
        this.data = data;
    }
}