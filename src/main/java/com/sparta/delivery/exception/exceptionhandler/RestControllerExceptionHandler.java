package com.sparta.delivery.exception.exceptionhandler;

import com.sparta.delivery.exception.ExceptionRes;
import com.sparta.delivery.exception.FieldException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class RestControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e){

        return ResponseEntity
                .badRequest()
                .body(ExceptionRes.of(e.getConstraintViolations()));
    }

    @ExceptionHandler(FieldException.class)
    public ResponseEntity fieldException(FieldException e){

        return ResponseEntity
                .badRequest()
                .body(ExceptionRes.of(e.getField(),e.getRejectedValue(),e.getReason()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e){

        return ResponseEntity
                .badRequest()
                .body(ExceptionRes.of(e.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity bindException(BindException e){

        return ResponseEntity
                .badRequest()
                .body(ExceptionRes.of(e.getBindingResult(),"주어진 입력값을 잘 확인해 주세요"));
    }

}
