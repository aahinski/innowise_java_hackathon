package com.innowise.hackathon.dto;

import com.innowise.hackathon.entity.CurrencyEntity;
import com.innowise.hackathon.entity.ChangeRateTrigger;

import java.util.List;

public record UserDto(String chatId, ChangeRateTrigger changeRateTrigger, List<CurrencyEntity> currencies) {

}
