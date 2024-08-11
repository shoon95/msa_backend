package com.sparta.msa_exam.order.exception;

import com.sparta.msa_exam.order.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductDoesNotExistException.class)
    public ResponseEntity<ResponseDto<Void>> handleProductDoesNotExistException(ProductDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.error(exception.getMessage()));
    }

    @ExceptionHandler(GetProductException.class)
    public ResponseEntity<ResponseDto<Void>> handlGetProductException(GetProductException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.error(exception.getMessage()));
    }
}
