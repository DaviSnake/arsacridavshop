package com.aracridav.aracridavback.dto;

import com.aracridav.aracridavback.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    int id;
    String userName;
    String fullName;
    String password;
    Rol rol;
    String image;
}
