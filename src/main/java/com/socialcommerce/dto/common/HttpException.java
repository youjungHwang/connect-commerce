package com.socialcommerce.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HttpException extends RuntimeException {
    private boolean ok;
    private String message;
    private HttpStatus httpStatus;
}