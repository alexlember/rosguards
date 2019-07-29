package ru.lember.rosguards.battlefield;

import lombok.Getter;
import ru.lember.rosguards.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

    @Getter
    private Status status = Status.FIGHTING;

    private List<Street> streets = new ArrayList<>();

    public boolean anySpaceLeft() {
        return true;
    }

    public void addPeopleToStreets(int peopleAmount) {
        RandomUtils.getRndListIndex(streets);
    }

}
