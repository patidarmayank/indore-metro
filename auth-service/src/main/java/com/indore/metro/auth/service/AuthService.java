package com.indore.metro.auth.service;

import com.indore.metro.auth.dto.LoginRequestDto;
import com.indore.metro.auth.dto.LoginResponseDto;
import com.indore.metro.auth.dto.RegisterRequestDto;

public interface AuthService {

    void register(RegisterRequestDto request) throws Exception;
    LoginResponseDto login(LoginRequestDto request);
}
