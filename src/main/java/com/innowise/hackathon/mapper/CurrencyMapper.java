package com.innowise.hackathon.mapper;

import com.innowise.hackathon.dto.CurrencyDto;
import com.innowise.hackathon.entity.CurrencyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

	CurrencyDto toDto(CurrencyEntity entity);

	CurrencyEntity toEntity(CurrencyDto dto);
}
