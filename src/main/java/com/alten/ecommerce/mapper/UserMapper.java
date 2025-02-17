package com.alten.ecommerce.mapper;

import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}