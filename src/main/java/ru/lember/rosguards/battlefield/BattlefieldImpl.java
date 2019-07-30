package ru.lember.rosguards.battlefield;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class BattlefieldImpl implements Battlefield {

	@Value("${people.maxAmountInTheStreet}")
	private Integer maxAmountInTheStreet;

	@Value("${rosguards.carCapacity}")
	private Integer carCapacity;

    @Getter
    private Status status = Status.FIGHTING;

    private Map<String, Street> streets;

	@PostConstruct
	private void postConstruct() {
		log.info("initialized. maxAmountInTheStreet: {}, carCapacity: {}, streets: {}", maxAmountInTheStreet, carCapacity, streets);
	}

	public BattlefieldImpl(Map<String, Street> streets) {
		this.streets = new ConcurrentHashMap<>(streets);
	}

	public boolean anySpaceLeft() {
		return streets.values().stream().anyMatch(s -> s.anySpaceLeft(maxAmountInTheStreet));
	}

	public boolean allStreetsAreEmpty() {
		return streets.values().stream().allMatch(Street::isEmpty);
	}

    public void addPeopleToStreets(int peopleAmount) {
		String rndKey = getRndEmptyStreetKey();
		Street street = streets.get(rndKey);
		if (street != null) {
			street.increaseAmount(peopleAmount);
		}
    }

	public RemovePeopleResult removePeopleFromStreets(String streetName) {
		Street street = streets.get(streetName);

    	if (street == null) {
    		return RemovePeopleResult.NO_SUCH_STREET;
		}

		if (!street.anySpaceLeft(maxAmountInTheStreet)) {
			return RemovePeopleResult.STREET_IS_FULL;
		}

		street.decreaseAmount(carCapacity);

		return RemovePeopleResult.CAR_IS_SUCCESSFULLY_LOADED;
	}

	private String getRndEmptyStreetKey() {
		List<Map.Entry<String, Street>> nonEmptyStreets = streets.entrySet().stream()
				.filter(s -> s.getValue().anySpaceLeft(maxAmountInTheStreet)).collect(Collectors.toList());

		int size = nonEmptyStreets.size();
		if (size == 0) {
			return "errorKey";
		}

		int randomIndex = new Random().nextInt(size);
		return nonEmptyStreets.get(randomIndex).getKey();
	}

	public List<Street> getStreetStatistics() {
		return new ArrayList<>(streets.values());
	}

	public Status getActualStatus() {

		if (status == Status.FIGHTING) {
			if (!anySpaceLeft()) {
				status = Status.GAME_OVER;
			} else if (allStreetsAreEmpty()) {
				status = Status.TRIUMPH;
			}
		}

		return status;

	}
}
