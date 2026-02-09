package com.aracridav.aracridavback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aracridav.aracridavback.utils.UtilsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/load")
@RequiredArgsConstructor
public class LoadDataController {

    private final UtilsService utilsService;
    

    @PostMapping
    public ResponseEntity<String> getAllUsers() {

        try {
            utilsService.deleteData();
            utilsService.loadeData();
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return ResponseEntity.ok("User guardados");
    }

}
