package com.innowise.hackathon.entity;

import java.util.Arrays;

public enum ChangeRateTrigger {
	THREE(3),
	FIVE(5),
	TEN(10),
	FIFTEEN(15);

	private final int value;

	ChangeRateTrigger(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ChangeRateTrigger fromValue(final int value) {
		return Arrays.stream(values())
				.filter(num -> num.value == value)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Invalid value: " + value));
	}
}
