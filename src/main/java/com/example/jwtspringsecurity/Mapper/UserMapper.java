package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.ResponseUserProject;
import com.example.jwtspringsecurity.dto.UserDTO;
import com.example.jwtspringsecurity.enities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDTOToUser(UserDTO userDTO);
    User userDTOtoResponseUserProject(ResponseUserProject ResponseUserProject);
    ResponseUserProject userToResponseUserProject(User user);
}
