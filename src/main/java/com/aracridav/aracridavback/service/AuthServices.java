package com.aracridav.aracridavback.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aracridav.aracridavback.dto.UserDto;
import com.aracridav.aracridavback.jwt.JwtServices;
import com.aracridav.aracridavback.model.User;
import com.aracridav.aracridavback.repository.UserRepository;
import com.aracridav.aracridavback.request.LoginRequest;
import com.aracridav.aracridavback.request.RegisterRequest;
import com.aracridav.aracridavback.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServices {

    private final UserRepository userRepository;
    private final JwtServices jwtServices;
    private final UserService userServices;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        UserDetails user = userRepository.findByUserName(request.getUserName()).orElseThrow();
        UserDto userDTO = userServices.getUserByName(user.getUsername());
        String token = jwtServices.getToken(user, userDTO.getRol().name());
        String refreshToken = jwtServices.getRefreshToken(user, userDTO.getRol().name());
        return AuthResponse.builder()
            .id(userDTO.getId())
            .token(token)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {

         User user = User.builder()
            .userName(request.getUserName())
            .fullName(request.getFullName())
            .password(passwordEncoder.encode(request.getPassword()))
            .rol(request.getRol())
            .build();
        
        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtServices.getToken(user, user.getRol().name()))
            .refreshToken(jwtServices.getRefreshToken(user, user.getRol().name()))
            .build();
    }

     public AuthResponse refresh(Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String userName = JwtServices.getUserNameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        UserDto userDTO = userServices.getUserByName(userName);

        if (jwtServices.isTokenValid(refreshToken, userDetails)){
            String newToken = jwtServices.getToken(userDetails, userDTO.getRol().name());
            String newRefreshToken = jwtServices.getRefreshToken(userDetails, userDTO.getRol().name());

            return AuthResponse.builder()
            .id(userDTO.getId())
            .token(newToken)
            .refreshToken(newRefreshToken)
            .build();
        }
        return null;
     }

}
