package com.innowise.hackathon.telegram;

import com.innowise.hackathon.dto.UserDto;
import com.innowise.hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

	private final UserService userService;

	@Override
	public void onUpdateReceived(final Update update) {
		Long chatId = update.getMessage().getChatId();

		if (!isExisted(String.valueOf(chatId))) {
			sendMessage(String.valueOf(chatId), "Your favorite currencies?");
			// Also button instead of message can be added
			// Then read their answer of favorites currencies
			// Same with change rate trigger
			var user = new UserDto(chatId, changeRateTrigger, currencies);
			userService.saveUser(user);
		}
	}

	// Add button to get all the currencies rates

	@Override
	public String getBotUsername() {
		return "YourBotUsername";
	}

	@Override
	public String getBotToken() {
		return "YourBotToken";
	}

	public void sendMessage(final String chatId, final String messageText) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(messageText);
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public boolean isExisted(final String chatId) {
		List<UserDto> users = userService.getAllUsers();
		return users.stream().anyMatch(user -> user.chatId().equals(chatId));
	}

}

