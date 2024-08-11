package com.sparta.msa_exam.gateway.exception;

import com.sparta.msa_exam.gateway.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MalformedJwtException.class, SignatureException.class, UnsupportedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<ResponseDto<Void>> handleInvalidJwtException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.error("Invalid or Expired token"));
    }
}
