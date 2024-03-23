package com.innowise.hackathon.service;

import com.innowise.hackathon.data.CurrencyRepository;
import com.innowise.hackathon.dto.CurrencyDto;
import com.innowise.hackathon.entity.CurrencyEntity;
import com.innowise.hackathon.mapper.CurrencyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

	private final CurrencyRepository currencyRepository;

	private final CurrencyMapper currencyMapper;

	public List<CurrencyDto> getAllCurrencies() {
		List<CurrencyDto> dtoList = currencyRepository.findAll().stream()
				.map(currencyMapper::toDto)
				.toList();
		return dtoList;
	}

	public void saveCurrency(final CurrencyDto dto) {
		CurrencyEntity entity = currencyMapper.toEntity(dto);
		currencyRepository.save(entity);
	}

}
