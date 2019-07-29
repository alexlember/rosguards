package ru.lember.rosguards;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static int getRndListIndex(@NonNull List<?> list) {
        int size = list.size();
        return new Random().nextInt(size);
    }
}
