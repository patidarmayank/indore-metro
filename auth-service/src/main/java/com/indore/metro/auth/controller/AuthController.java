package com.indore.metro.auth.controller;

import com.indore.metro.auth.dto.LoginRequestDto;
import com.indore.metro.auth.dto.LoginResponseDto;
import com.indore.metro.auth.dto.RegisterRequestDto;
import com.indore.metro.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request){

        try {
            authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authService.login(request));
    }


    //just for authentication and authorization token testing
    @GetMapping("/protected")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("You are authenticated!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String>  admin(){
        return ResponseEntity.ok("you are in admin");
    }
}
