package com.innowise.hackathon.scheduled;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyJson {

	@JsonProperty("symbol")
	private String name;

	@JsonProperty("price")
	private String price;

}
