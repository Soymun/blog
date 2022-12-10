package com.example.posts.Mappers;

import com.example.posts.DTO.UserDto;
import com.example.posts.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
