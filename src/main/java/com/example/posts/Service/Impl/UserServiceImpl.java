package com.example.posts.Service.Impl;

import com.example.posts.DTO.UserDto;
import com.example.posts.Entity.User;
import com.example.posts.Mappers.UserMapper;
import com.example.posts.Repositories.UserRepositories;
import com.example.posts.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepositories userRepositories;


    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepositories userRepositories, UserMapper userMapper) {
        this.userRepositories = userRepositories;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.equals("")){
            throw new RuntimeException("User not found");
        }
        User user = getUserByEmail(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRole().authorities());
    }

    @Override
    public String saveUser(User userDto) {
        log.info("Save user {}", userDto);
        userRepositories.save(userDto);
        return "Suggest";
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id {}", id);
        return userMapper.userToUserDto(userRepositories.findUserById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepositories.findUserByEmail(email).orElse(null);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.info("Update user {}", userDto);
        User user = userRepositories.findUserById(userDto.getId()).orElseThrow(()-> new RuntimeException("User not found"));
        user.setAbout(userDto.getAbout());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        return userMapper.userToUserDto(userRepositories.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Delete user with id {}", id);
        userRepositories.deleteById(id);
    }
}
