package com.sparta.delivery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String field;
    private String rejectedValue;
    private String reason;
}
