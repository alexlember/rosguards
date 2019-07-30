package ru.lember.rosguards.battlefield;

import java.util.List;

public interface Battlefield {

	boolean anySpaceLeft();
	boolean allStreetsAreEmpty();
	void addPeopleToStreets(int peopleAmount);
	RemovePeopleResult removePeopleFromStreets(String streetName);
	Status getActualStatus();
	List<Street> getStreetStatistics();

}
