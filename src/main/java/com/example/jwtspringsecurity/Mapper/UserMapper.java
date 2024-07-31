package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.ResponseUserProject;
import com.example.jwtspringsecurity.dto.UserDTO;
import com.example.jwtspringsecurity.enities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "dob", target = "dob"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "userType", target = "userType"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "salaryDate", target = "salaryDate"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "level", target = "level"),
            @Mapping(source = "sex", target = "sex"),
            @Mapping(source = "branchId", target = "branch.id"),
            @Mapping(source = "positionId", target = "position.id")
    })
    User userDTOToUser(UserDTO userDTO);
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "dob", target = "dob"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "userType", target = "userType"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "salaryDate", target = "salaryDate"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "level", target = "level"),
            @Mapping(source = "sex", target = "sex"),
            @Mapping(source = "branch.id", target = "branchId"),
            @Mapping(source = "position.id", target = "positionId")
    })
    UserDTO USER_DTOToUsers(User user);
    User userDTOtoResponseUserProject(ResponseUserProject ResponseUserProject);
    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "level", target = "level"),
            @Mapping(source = "sex", target = "sex"),
            @Mapping(source = "userType", target = "userType")
    })
    ResponseUserProject userToResponseUserProjects(User user);
}
