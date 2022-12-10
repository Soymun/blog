package com.example.posts.Service;

import com.example.posts.DTO.UserDto;
import com.example.posts.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String saveUser(User userDto);

    UserDto getUserById(Long id);

    User getUserByEmail(String email);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);
}
