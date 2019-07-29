package ru.lember.rosguards;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.lember.rosguards.battlefield.Statistics;
import ru.lember.rosguards.battlefield.Status;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class PeoplesFactory {

    @Value("${people.maxAmount}")
    private Integer maxAmount;

//    @Value("${people.rateMs}")
//    private Integer rateMs;

    @Value("${people.increasingAmount}")
    private Integer increasingAmount;

    private AtomicInteger peoplesNumber = new AtomicInteger(0);


    private final Statistics statistics;

    @Autowired
    public PeoplesFactory(Statistics statistics) {
        this.statistics = statistics;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("Initialized. " +
                        "maxPeopleNumber: {}, " +
                        "increasingAmount: {}",
                maxAmount, increasingAmount);
    }

    @Scheduled(
            initialDelay = 1000L,
            fixedDelayString = "${people.increasingAmount}")
    private void increasePeopleAmount() {

        log.info("increasePeopleAmount. peoplesNumber: {}", peoplesNumber);

        peoplesNumber.addAndGet(increasingAmount);


    }

    private Status getActualStatus() {

        if (statistics.getStatus() == Status.GAME_OVER) {
            return Status.GAME_OVER;
        } else if (statistics.getStatus() == Status.TRIUMPH) {
            return Status.TRIUMPH;
        }


        if (peoplesNumber.get() >= maxAmount) {
            return Status.TRIUMPH;
        } else if (!statistics.anySpaceLeft()) {
            return Status.GAME_OVER;
        } else {
            return Status.FIGHTING;
        }

    }

}
