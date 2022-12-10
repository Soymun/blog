package com.example.posts.Controllers;

import com.example.posts.DTO.UserDto;
import com.example.posts.Response.ResponseDto;
import com.example.posts.Service.Impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
@CrossOrigin
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(userService.getUserById(id)).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(ResponseDto.builder().body(userService.updateUser(userDto)).build());
    }

}
