package ru.lember.rosguards.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.lember.rosguards.battlefield.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RestController
public class RosGuardsController {

	private static final String SUCCESS_PRISON_CAR_SENT_TEMPLATE = "Автозаки был успешно направлены на улицу %s";
	private static final String FAILED_PRISON_CAR_SENT_TEMPLATE = "Улица %s уже заполнена людьми! Автозаки не смогли проехать!";
	private static final String FAILED_PRISON_CAR_SENT_WRONG_REQUEST = "Запрос некорректный, необходимо указать улицу!";
	private static final String FAILED_PRISON_CAR_SENT_WRONG_STREET_TEMPLATE = "Запрос некорректный, улицы %s не существует!";

	private Battlefield battlefield;

	@Value("${people.maxAmountInTheStreet}")
	private Integer maxAmountInTheStreet;

	@PostConstruct
	private void postConstruct() {
		log.info("initialized. maxAmountInTheStreet: {}", maxAmountInTheStreet);
	}

	@Autowired
	public void setBattlefield(Battlefield battlefield) {
		this.battlefield = battlefield;
	}

	@PostMapping(value = "prisonCar")
	public String prisonCar(@RequestBody PrisonCarRequest request) {

		log.info("RosGuardsController#prisonCar. request: {}", request);

		if (request == null || StringUtils.isEmpty(request.getStreetName())) {
			return FAILED_PRISON_CAR_SENT_WRONG_REQUEST;
		}

		String streetName = request.getStreetName();
		RemovePeopleResult result = battlefield.removePeopleFromStreets(streetName);

		if (result == RemovePeopleResult.NO_SUCH_STREET) {
			return String.format(FAILED_PRISON_CAR_SENT_WRONG_STREET_TEMPLATE, streetName);
		} else if (result == RemovePeopleResult.STREET_IS_FULL) {
			return String.format(FAILED_PRISON_CAR_SENT_TEMPLATE, streetName);
		} else {
			return String.format(SUCCESS_PRISON_CAR_SENT_TEMPLATE, streetName);
		}
	}

	@GetMapping(value = "status")
	public StatusResponse status() {

		log.info("RosGuardsController#status");

		StatusResponse response = new StatusResponse();

		Status status = battlefield.getActualStatus();
		List<Street> streets = battlefield.getStreetStatistics();

		response.setStatus(status.getValue());
		response.setStreets(streets);
		response.setMaxAmountInTheStreet(maxAmountInTheStreet);

		log.info("RosGuardsController#status. response: {}", response);

		return response;
	}
}
