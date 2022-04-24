package com.example.demo.user.mapper;

import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.dto.UserMinimalDto;
import com.example.demo.user.dto.UserUpdateDto;
import com.example.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDto userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDto userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDto userListDto) {
        userListDto.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    UserUpdateDto userUpdateDtoFromUser(User user);
}