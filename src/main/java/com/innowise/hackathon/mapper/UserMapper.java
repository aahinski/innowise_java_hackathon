package com.innowise.hackathon.mapper;

import com.innowise.hackathon.dto.UserDto;
import com.innowise.hackathon.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toDto(UserEntity entity);

	UserEntity toEntity(UserDto dto);
}
