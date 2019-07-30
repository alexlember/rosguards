package ru.lember.rosguards.controller;

import lombok.Data;
import ru.lember.rosguards.battlefield.Street;

import java.util.List;

@Data
class StatusResponse {

	private String status;
	private Integer maxAmountInTheStreet;
	private List<Street> streets;
}
