package ru.lember.rosguards.battlefield;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Street {

	@Getter
    private String name;
	@Getter
    private Integer peopleAmount = 50;

	public Street(String name) {
		this.name = name;
	}

	boolean isEmpty() {
		return peopleAmount == 0;
	}

	boolean anySpaceLeft(int maxValue) {
		return peopleAmount < maxValue;
	}

	void increaseAmount(int people) {
		peopleAmount += people;
	}

	void decreaseAmount(int people) {

		int finalAmount = peopleAmount - people;
		if (finalAmount < 0) {
			peopleAmount = 0;
		} else {
			peopleAmount = finalAmount;
		}
	}
}
