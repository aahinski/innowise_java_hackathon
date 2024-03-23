package com.innowise.hackathon.service;

import com.innowise.hackathon.data.CurrencyRepository;
import com.innowise.hackathon.data.UserRepository;
import com.innowise.hackathon.dto.CurrencyDto;
import com.innowise.hackathon.dto.UserDto;
import com.innowise.hackathon.entity.ChangeRateTrigger;
import com.innowise.hackathon.entity.UserEntity;
import com.innowise.hackathon.mapper.CurrencyMapper;
import com.innowise.hackathon.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final CurrencyRepository currencyRepository;

	private final UserRepository userRepository;

	private final CurrencyMapper currencyMapper;

	private final UserMapper userMapper;

	public void saveUser(final UserDto dto) {
		UserEntity entity = userMapper.toEntity(dto);
		userRepository.saveUser(entity);
		userRepository.saveUserCurrencies(entity.id(), entity.currencies());
	}

	public List<UserDto> getUsersByCurrencyAndChangeRate(final CurrencyDto currency, final int rate) {
		var changeRate = ChangeRateTrigger.fromValue(rate);
		List<UserDto> dtoList =
				userRepository.findAllByCurrencyAndChangeRate(currency.name(), changeRate.name()).stream()
						.map(userMapper::toDto)
						.toList();
		return dtoList;
	}

	public List<UserDto> getAllUsers() {
		List<UserDto> dtoList = userRepository.findAll().stream()
				.map(userMapper::toDto)
				.toList();
		return dtoList;
	}

}
