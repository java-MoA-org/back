package com.MoA.moa_back.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.MoA.moa_back.common.dto.response.ResponseDto;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        HttpMessageNotReadableException.class
    })
    public ResponseEntity<ResponseDto> validExceptionHandler(Exception e){
        e.printStackTrace();
        return ResponseDto.validationFail();
    }
}
