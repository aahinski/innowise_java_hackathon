package com.innowise.hackathon.entity;

import java.util.List;
import java.util.UUID;

public record UserEntity(UUID id, String chatId, ChangeRateTrigger changeRateTrigger, List<CurrencyEntity> currencies) {

}
