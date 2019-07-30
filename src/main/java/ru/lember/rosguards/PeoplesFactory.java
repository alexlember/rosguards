package ru.lember.rosguards;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.lember.rosguards.battlefield.Battlefield;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class PeoplesFactory {

    @Value("${people.maxAmount}")
    private Integer maxAmount;

    @Value("${people.increasingAmount}")
    private Integer increasingAmount;

    private AtomicInteger peoplesNumber = new AtomicInteger(0);

    private final Battlefield battlefield;

    @Autowired
    public PeoplesFactory(Battlefield battlefield) {
        this.battlefield = battlefield;
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
            fixedDelayString = "${people.rateMs}")
    private void increasePeopleAmount() {

        if (peoplesNumber.get() >= maxAmount) {
        	log.info("increasePeopleAmount. can't increase people anymore. All people are already in the streets");
		} else if (!battlefield.anySpaceLeft()) {
			log.debug("increasePeopleAmount. Streets are full! No need to go out anymore! Total peoplesNumber: {}/{}", peoplesNumber, maxAmount);
		} else {
			log.info("increasePeopleAmount. total peoplesNumber: {}/{}", peoplesNumber, maxAmount);
			peoplesNumber.addAndGet(increasingAmount);
			battlefield.addPeopleToStreets(increasingAmount);
		}

    }

}
