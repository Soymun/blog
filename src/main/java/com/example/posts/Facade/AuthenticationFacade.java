package com.example.posts.Facade;

import com.example.posts.DTO.LoginDto;
import com.example.posts.DTO.RegistrationDto;
import com.example.posts.Entity.Role;
import com.example.posts.Entity.User;
import com.example.posts.Security.JwtTokenProvider;
import com.example.posts.Service.Impl.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFacade {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationFacade(JwtTokenProvider jwtTokenProvider, UserServiceImpl userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String registration(RegistrationDto registrationDto){
        if(userService.getUserByEmail(registrationDto.getEmail()) != null){
            throw new RuntimeException("User already exist");
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setUserName(registrationDto.getUserName());
        user.setRole(Role.USER);
        return userService.saveUser(user);
    }

    public Map<Object, Object> login(LoginDto loginDto){
        try {
            User user = userService.getUserByEmail(loginDto.getEmail());
            if(user == null){
                throw new RuntimeException("User not found");
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            String token = jwtTokenProvider.createToken(loginDto.getEmail(), user.getRole());
            Map<Object, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("role", user.getRole());
            map.put("token", token);
            return map;
        }
        catch (AuthenticationException e){
            throw new RuntimeException("Login is possible");
        }
    }
}
