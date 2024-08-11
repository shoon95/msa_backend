package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.domain.User;
import com.sparta.msa_exam.auth.dto.ResponseDto;
import com.sparta.msa_exam.auth.dto.SignInResponseDto;
import com.sparta.msa_exam.auth.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpResponseDto;
import com.sparta.msa_exam.auth.exception.UsernameAlreadyExistException;
import com.sparta.msa_exam.auth.repository.UserRepository;
import com.sparta.msa_exam.auth.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    public ResponseDto<SignUpResponseDto> signUp(String username, String password) {

        // 아이디 중복 검사
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistException("username already exist");
        }

        User user = User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .build();

        userRepository.save(user);

        return ResponseDto.success("User created successfully", new SignUpResponseDto(username));
    }

    public ResponseDto<SignInResponseDto> signIn(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find username"));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        String accessToken = jwtUtils.generateToken(username);
        SignInResponseDto signInResponseDto = new SignInResponseDto(accessToken);
        return ResponseDto.success("login success", signInResponseDto);

    }
}
