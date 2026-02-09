package com.aracridav.aracridavback.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aracridav.aracridavback.dto.UserDto;
import com.aracridav.aracridavback.model.User;
import com.aracridav.aracridavback.repository.UserRepository;
import com.aracridav.aracridavback.request.UserRequest;
import com.aracridav.aracridavback.response.UserResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; 

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) throws IOException{

        return new UserResponse("El usuario se registró satisfactoriamente");
    }

    public UserDto getUserByName(String name) {
        User user = userRepository.findByUserName(name).orElse(null);
       
        if (user!=null)
        {
            UserDto userDto = UserDto.builder()
            .id(user.id)
            .userName(user.userName)
            .fullName(user.fullName)
            .rol(user.rol)
            .image(user.image)
            .build();
            return userDto;
        }
        return null;
    }
    
    public List<UserDto> getAllUsers(){

        List<User> users = userRepository.findAll();

        {
            return users
                .stream()
                .map(user -> new UserDto(user.id, user.userName, user.fullName, user.password, user.rol, user.image))
                .collect(Collectors.toList());
        }
    }

}
