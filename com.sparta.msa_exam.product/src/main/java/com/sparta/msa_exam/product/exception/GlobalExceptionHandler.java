package com.sparta.msa_exam.product.exception;

import com.sparta.msa_exam.product.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductDoesNotExistException.class)
    public ResponseEntity<ResponseDto<Void>> handleProductDoesNotExistException(ProductDoesNotExistException exception) {
        ResponseDto<Void> responseDto = ResponseDto.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }}
