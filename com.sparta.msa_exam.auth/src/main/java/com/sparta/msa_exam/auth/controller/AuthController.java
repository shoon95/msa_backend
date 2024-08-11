package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.*;
import com.sparta.msa_exam.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        ResponseDto<SignUpResponseDto> signUpResponse = authService.signUp(signUpRequestDto.getUsername(), signUpRequestDto.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<SignInResponseDto>> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        ResponseDto<SignInResponseDto> signInResponse = authService.signIn(signInRequestDto.getUsername(), signInRequestDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(signInResponse);
    }
}
