package com.innowise.hackathon.scheduled;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.hackathon.dto.CurrencyDto;
import com.innowise.hackathon.dto.UserDto;
import com.innowise.hackathon.entity.ChangeRateTrigger;
import com.innowise.hackathon.service.CurrencyService;
import com.innowise.hackathon.service.UserService;
import com.innowise.hackathon.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class RateUpdater {

	private final CurrencyService currencyService;

	private final UserService userService;

	private final CurrencyJsonMapper jsonMapper;

	private final TelegramBot telegramBot;

	@Scheduled(fixedRate = 30000)
	public void updateCurrencies() {
		try {
			Connection.Response response = Jsoup.connect("https://api.mexc.com/api/v3/ticker/price")
					.ignoreContentType(true) // Ignore content type to get raw JSON response
					.execute();
			String jsonResponse = response.body();
			ObjectMapper objectMapper = new ObjectMapper();
			List<CurrencyJson> currencyDataList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
			List<CurrencyDto> currentCurrencyDtoList = currencyService.getAllCurrencies();
			List<CurrencyDto> newCurrencyDtoList = currencyDataList.stream()
					.map(jsonMapper::toDto)
					.toList();
			createNotificationProcess(currentCurrencyDtoList, newCurrencyDtoList);
			newCurrencyDtoList.forEach(currencyService::saveCurrency);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createNotificationProcess(
			final List<CurrencyDto> currentCurrencyDtoList,
			final List<CurrencyDto> newCurrencyDtoList
	) {
		List<Integer> changeRates = Arrays.stream(ChangeRateTrigger.values())
				.map(ChangeRateTrigger::getValue)
				.toList();

		for (var rate : changeRates) {
			List<CurrencyDto> currenciesWithRate =
					getCurrenciesWithChangeRate(currentCurrencyDtoList, newCurrencyDtoList, rate);
			for (var currency : currenciesWithRate) {
				getUsersByCurrencyAndChangeRate(currency, rate).forEach(user -> telegramBot.sendMessage(
						user.chatId(),
						"Currency " + currency.name() + " is now worth " + currency.price()
				));
			}
		}
	}

	private List<CurrencyDto> getCurrenciesWithChangeRate(
			final List<CurrencyDto> currentCurrencyDtoList,
			final List<CurrencyDto> newCurrencyDtoList,
			final int changeRate
	) {
		return currentCurrencyDtoList.stream()
				.flatMap(currentCurrency ->
						newCurrencyDtoList.stream()
								.filter(newCurrency -> currentCurrency.name().equals(newCurrency.name()))
								.filter(newCurrency -> {
									double priceDifference = Math.abs(currentCurrency.price() - newCurrency.price());
									double priceDifferencePercentage = priceDifference / currentCurrency.price() * 100;
									return priceDifferencePercentage > changeRate;
								})
				)
				.toList();
	}

	private List<UserDto> getUsersByCurrencyAndChangeRate(final CurrencyDto currency, final int changeRate) {
		return userService.getUsersByCurrencyAndChangeRate(currency, changeRate);
	}

}
