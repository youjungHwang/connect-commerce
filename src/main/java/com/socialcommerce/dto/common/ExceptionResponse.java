package com.socialcommerce.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private Boolean ok;
    private int statusCode;
    private String message;

    public ExceptionResponse(
            Boolean ok,
            String message,
            int statusCode

    ) {
        this.ok = ok;
        this.message = message;
        this.statusCode = statusCode;
    }

}