package com.aracridav.aracridavback.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aracridav.aracridavback.dto.UserDto;
import com.aracridav.aracridavback.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users= userService.getAllUsers();
        if (users==null)
        {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

}
