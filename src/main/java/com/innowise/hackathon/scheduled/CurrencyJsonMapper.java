package com.innowise.hackathon.scheduled;

import com.innowise.hackathon.dto.CurrencyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyJsonMapper {

	CurrencyDto toDto(CurrencyJson json);

}

