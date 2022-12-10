package com.example.posts.Controllers;

import com.example.posts.DTO.LoginDto;
import com.example.posts.DTO.RegistrationDto;
import com.example.posts.Facade.AuthenticationFacade;
import com.example.posts.Response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
@CrossOrigin
public class AuthenticationControllers {

    private final AuthenticationFacade authenticationFacade;

    public AuthenticationControllers(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto){
        return ResponseEntity.ok(ResponseDto.builder().body(authenticationFacade.registration(registrationDto)).build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(ResponseDto.builder().body(authenticationFacade.login(loginDto)).build());
    }
}
