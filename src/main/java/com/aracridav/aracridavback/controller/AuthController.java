package com.aracridav.aracridavback.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aracridav.aracridavback.request.LoginRequest;
import com.aracridav.aracridavback.request.RegisterRequest;
import com.aracridav.aracridavback.response.AuthResponse;
import com.aracridav.aracridavback.service.AuthServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    private final AuthServices authService; 

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        
        return ResponseEntity.ok(authService.login(request));

    }
    
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        
        return ResponseEntity.ok(authService.register(request));
        
    }
    
    @PostMapping(value = "refresh")
    public ResponseEntity<AuthResponse> login(@RequestBody Map<String, String> request){
        
        return ResponseEntity.ok(authService.refresh(request));

    }
}
