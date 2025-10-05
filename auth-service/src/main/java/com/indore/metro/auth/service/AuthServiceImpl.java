package com.indore.metro.auth.service;

import com.indore.metro.auth.dao.AuthDao;
import com.indore.metro.auth.dto.LoginRequestDto;
import com.indore.metro.auth.dto.LoginResponseDto;
import com.indore.metro.auth.dto.RegisterRequestDto;
import com.indore.metro.auth.model.User;
import com.indore.metro.auth.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthDao authDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;




    public AuthServiceImpl(AuthDao authDao, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authDao = authDao;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    @Override
    public void register(RegisterRequestDto request) throws Exception {
        if (authDao.findByEmail(request.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        authDao.save(user);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        User user= authDao.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );

        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new LoginResponseDto(accessToken, refreshToken);
    }
}
