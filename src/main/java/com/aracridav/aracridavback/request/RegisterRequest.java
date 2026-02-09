package com.aracridav.aracridavback.request;

import com.aracridav.aracridavback.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    String userName;
    String password;
    String fullName;
    Rol rol;
}
